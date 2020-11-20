import axios from "axios";
import { TOKEN_NAME, TOKEN_PREFIX } from "./types";
import {getJWT} from "./JWT";

export function getInstance() {
    let token = getJWT(TOKEN_NAME);
    if (token) {
        token = TOKEN_PREFIX.concat(token);
    }
    let axiosInst = axios.create({
        headers: {'Authorization': token},
        baseURL: 'http://localhost:8080/',
    });
    return axiosInst;
}