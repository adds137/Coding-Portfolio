import {TOKEN_NAME} from "./types"
import jwt_decode from "jwt-decode";
import Cookies from "js-cookie";
/* 
kirlich, Arseniy-II, May 2020, Get cookie by name, Stackoverflow,
 * viewed on 12/10/2020, 
 * <https://stackoverflow.com/questions/10730362/get-cookie-by-name> 
 */

export function getJWT() {
    return Cookies.get(TOKEN_NAME);
}

export function getUsername() {
    let jwtString = getJWT();
    if (jwtString) {
        jwtString = jwt_decode(jwtString).sub;
    }
    return jwtString;
}