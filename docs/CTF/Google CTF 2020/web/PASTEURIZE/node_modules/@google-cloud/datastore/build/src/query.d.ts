/*!
 * Copyright 2014 Google LLC.
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
/// <reference types="node" />
import { Key } from 'readline';
import { Datastore } from '.';
import { Entity } from './entity';
import { Transaction } from './transaction';
import { CallOptions } from 'google-gax';
import { RunQueryStreamOptions } from '../src/request';
export declare type Operator = '=' | '<' | '>' | '<=' | '>=' | 'HAS_ANCESTOR';
export interface OrderOptions {
    descending?: boolean;
}
export interface Order {
    name: string;
    sign: '-' | '+';
}
export interface Filter {
    name: string;
    val: any;
    op: Operator;
}
/**
 * Build a Query object.
 *
 * **Queries are built with {module:datastore#createQuery} and
 * {@link Transaction#createQuery}.**
 *
 * @see [Datastore Queries]{@link http://goo.gl/Cag0r6}
 *
 * @class
 * @param {Datastore|Transaction} scope The parent scope the query was created
 *     from.
 * @param {string} [namespace] Namespace to query entities from.
 * @param {string[]} kinds Kind to query.
 *
 * @example
 * const {Datastore} = require('@google-cloud/datastore');
 * const datastore = new Datastore();
 * const query = datastore.createQuery('AnimalNamespace', 'Lion');
 */
declare class Query {
    scope?: Datastore | Transaction;
    namespace?: string | null;
    kinds: string[];
    filters: Filter[];
    orders: Order[];
    groupByVal: Array<{}>;
    selectVal: Array<{}>;
    startVal: string | Buffer | null;
    endVal: string | Buffer | null;
    limitVal: number;
    offsetVal: number;
    constructor(scope?: Datastore | Transaction, kinds?: string[] | null);
    constructor(scope?: Datastore | Transaction, namespace?: string | null, kinds?: string[]);
    filter(property: string, value: {}): Query;
    filter(property: string, operator: Operator, value: {}): Query;
    /**
     * Filter a query by ancestors.
     *
     * @see [Datastore Ancestor Filters]{@link https://cloud.google.com/datastore/docs/concepts/queries#datastore-ancestor-query-nodejs}
     *
     * @param {Key} key Key object to filter by.
     * @returns {Query}
     *
     * @example
     * const {Datastore} = require('@google-cloud/datastore');
     * const datastore = new Datastore();
     * const query = datastore.createQuery('MyKind');
     * const ancestoryQuery = query.hasAncestor(datastore.key(['Parent', 123]));
     */
    hasAncestor(key: Key): this;
    /**
     * Sort the results by a property name in ascending or descending order. By
     * default, an ascending sort order will be used.
     *
     * @see [Datastore Sort Orders]{@link https://cloud.google.com/datastore/docs/concepts/queries#datastore-ascending-sort-nodejs}
     *
     * @param {string} property The property to order by.
     * @param {object} [options] Options object.
     * @param {boolean} [options.descending=false] Sort the results by a property
     *     name in descending order.
     * @returns {Query}
     *
     * @example
     * const {Datastore} = require('@google-cloud/datastore');
     * const datastore = new Datastore();
     * const companyQuery = datastore.createQuery('Company');
     *
     * // Sort by size ascendingly.
     * const companiesAscending = companyQuery.order('size');
     *
     * // Sort by size descendingly.
     * const companiesDescending = companyQuery.order('size', {
     *   descending: true
     * });
     */
    order(property: string, options?: OrderOptions): this;
    /**
     * Group query results by a list of properties.
     *
     * @param {array} properties Properties to group by.
     * @returns {Query}
     *
     * @example
     * const {Datastore} = require('@google-cloud/datastore');
     * const datastore = new Datastore();
     * const companyQuery = datastore.createQuery('Company');
     * const groupedQuery = companyQuery.groupBy(['name', 'size']);
     */
    groupBy(fieldNames: string | string[]): this;
    /**
     * Retrieve only select properties from the matched entities.
     *
     * Queries that select a subset of properties are called Projection Queries.
     *
     * @see [Projection Queries]{@link https://cloud.google.com/datastore/docs/concepts/projectionqueries}
     *
     * @param {string|string[]} fieldNames Properties to return from the matched
     *     entities.
     * @returns {Query}
     *
     * @example
     * const {Datastore} = require('@google-cloud/datastore');
     * const datastore = new Datastore();
     * const companyQuery = datastore.createQuery('Company');
     *
     * // Only retrieve the name property.
     * const selectQuery = companyQuery.select('name');
     *
     * // Only retrieve the name and size properties.
     * const selectQuery = companyQuery.select(['name', 'size']);
     */
    select(fieldNames: string | string[]): this;
    /**
     * Set a starting cursor to a query.
     *
     * @see [Query Cursors]{@link https://cloud.google.com/datastore/docs/concepts/queries#cursors_limits_and_offsets}
     *
     * @param {string} cursorToken The starting cursor token.
     * @returns {Query}
     *
     * @example
     * const {Datastore} = require('@google-cloud/datastore');
     * const datastore = new Datastore();
     * const companyQuery = datastore.createQuery('Company');
     *
     * const cursorToken = 'X';
     *
     * // Retrieve results starting from cursorToken.
     * const startQuery = companyQuery.start(cursorToken);
     */
    start(start: string | Buffer): this;
    /**
     * Set an ending cursor to a query.
     *
     * @see [Query Cursors]{@link https://cloud.google.com/datastore/docs/concepts/queries#Datastore_Query_cursors}
     *
     * @param {string} cursorToken The ending cursor token.
     * @returns {Query}
     *
     * @example
     * const {Datastore} = require('@google-cloud/datastore');
     * const datastore = new Datastore();
     * const companyQuery = datastore.createQuery('Company');
     *
     * const cursorToken = 'X';
     *
     * // Retrieve results limited to the extent of cursorToken.
     * const endQuery = companyQuery.end(cursorToken);
     */
    end(end: string | Buffer): this;
    /**
     * Set a limit on a query.
     *
     * @see [Query Limits]{@link https://cloud.google.com/datastore/docs/concepts/queries#datastore-limit-nodejs}
     *
     * @param {number} n The number of results to limit the query to.
     * @returns {Query}
     *
     * @example
     * const {Datastore} = require('@google-cloud/datastore');
     * const datastore = new Datastore();
     * const companyQuery = datastore.createQuery('Company');
     *
     * // Limit the results to 10 entities.
     * const limitQuery = companyQuery.limit(10);
     */
    limit(n: number): this;
    /**
     * Set an offset on a query.
     *
     * @see [Query Offsets]{@link https://cloud.google.com/datastore/docs/concepts/queries#datastore-limit-nodejs}
     *
     * @param {number} n The offset to start from after the start cursor.
     * @returns {Query}
     *
     * @example
     * const {Datastore} = require('@google-cloud/datastore');
     * const datastore = new Datastore();
     * const companyQuery = datastore.createQuery('Company');
     *
     * // Start from the 101st result.
     * const offsetQuery = companyQuery.offset(100);
     */
    offset(n: number): this;
    run(options?: RunQueryOptions): Promise<RunQueryResponse>;
    run(options: RunQueryOptions, callback: RunQueryCallback): void;
    run(callback: RunQueryCallback): void;
    /**
     * Run the query as a readable object stream.
     *
     * @method Query#runStream
     * @param {object} [options] Optional configuration. See
     *     {@link Query#run} for a complete list of options.
     * @returns {stream}
     *
     * @example
     * const {Datastore} = require('@google-cloud/datastore');
     * const datastore = new Datastore();
     * const query = datastore.createQuery('Company');
     *
     * query.runStream()
     *   .on('error', console.error)
     *   .on('data', function (entity) {
     *     // Access the Key object for this entity.
     *     const key = entity[datastore.KEY];
     *   })
     *   .on('info', (info) => {})
     *   .on('end', () => {
     *     // All entities retrieved.
     *   });
     *
     * //-
     * // If you anticipate many results, you can end a stream early to prevent
     * // unnecessary processing and API requests.
     * //-
     * query.runStream()
     *   .on('data', function (entity) {
     *     this.end();
     *   });
     */
    runStream(options?: RunQueryStreamOptions): import("stream").Transform;
}
export interface QueryProto {
    startCursor?: string | Buffer;
    distinctOn: {};
    kind: {};
    order: {};
    projection: {};
    endCursor?: string | Buffer;
    limit?: {};
    offset?: number;
    filter?: {};
}
/**
 * Reference to the {@link Query} class.
 * @name module:@google-cloud/datastore.Query
 * @see Query
 */
export { Query };
export interface IntegerTypeCastOptions {
    integerTypeCastFunction: Function;
    properties?: string | string[];
}
export interface RunQueryOptions {
    consistency?: 'strong' | 'eventual';
    gaxOptions?: CallOptions;
    wrapNumbers?: boolean | IntegerTypeCastOptions;
}
export interface RunQueryCallback {
    (err: Error | null, entities?: Entity[], info?: RunQueryInfo): void;
}
export declare type RunQueryResponse = [Entity[], RunQueryInfo];
export interface RunQueryInfo {
    endCursor?: string;
    moreResults?: 'MORE_RESULTS_TYPE_UNSPECIFIED' | 'NOT_FINISHED' | 'MORE_RESULTS_AFTER_LIMIT' | 'MORE_RESULTS_AFTER_CURSOR' | 'NO_MORE_RESULTS';
}
