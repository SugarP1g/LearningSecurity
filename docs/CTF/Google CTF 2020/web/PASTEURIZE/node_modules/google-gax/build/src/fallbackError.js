"use strict";
/**
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
Object.defineProperty(exports, "__esModule", { value: true });
exports.FallbackErrorDecoder = void 0;
const protobuf = require("protobufjs");
const status_1 = require("./status");
class FallbackErrorDecoder {
    constructor() {
        // eslint-disable-next-line @typescript-eslint/no-var-requires
        const errorProtoJson = require('../../protos/status.json');
        this.root = protobuf.Root.fromJSON(errorProtoJson);
        this.anyType = this.root.lookupType('google.protobuf.Any');
        this.statusType = this.root.lookupType('google.rpc.Status');
    }
    decodeProtobufAny(anyValue) {
        const match = anyValue.type_url.match(/^type.googleapis.com\/(.*)/);
        if (!match) {
            throw new Error(`Unknown type encoded in google.protobuf.any: ${anyValue.type_url}`);
        }
        const typeName = match[1];
        const type = this.root.lookupType(typeName);
        if (!type) {
            throw new Error(`Cannot lookup type ${typeName}`);
        }
        return type.decode(anyValue.value);
    }
    // Decodes gRPC-fallback error which is an instance of google.rpc.Status.
    decodeRpcStatus(buffer) {
        const uint8array = new Uint8Array(buffer);
        const status = this.statusType.decode(uint8array);
        // google.rpc.Status contains an array of google.protobuf.Any
        // which need a special treatment
        const result = {
            code: status.code,
            message: status.message,
            details: status.details.map(detail => this.decodeProtobufAny(detail)),
        };
        return result;
    }
    // Construct an Error from a StatusObject.
    // Adapted from https://github.com/grpc/grpc-node/blob/master/packages/grpc-js/src/call.ts#L79
    callErrorFromStatus(status) {
        status.message = `${status.code} ${status_1.Status[status.code]}: ${status.message}`;
        return Object.assign(new Error(status.message), status);
    }
    // Decodes gRPC-fallback error which is an instance of google.rpc.Status,
    // and puts it into the object similar to gRPC ServiceError object.
    decodeErrorFromBuffer(buffer) {
        return this.callErrorFromStatus(this.decodeRpcStatus(buffer));
    }
}
exports.FallbackErrorDecoder = FallbackErrorDecoder;
//# sourceMappingURL=fallbackError.js.map