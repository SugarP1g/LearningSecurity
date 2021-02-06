import { RecaptchaResponseV2, RecaptchaResponseV3 } from '../interfaces';
declare global {
    namespace Express {
        interface Request {
            recaptcha?: RecaptchaResponseV2 | RecaptchaResponseV3;
        }
        interface Response {
            recaptcha?: string;
        }
    }
}
