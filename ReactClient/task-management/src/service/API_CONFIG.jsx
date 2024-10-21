import axios from "axios";

axios.defaults.baseURL = "http://localhost:8080/api";
axios.defaults.headers.post['Content-Type'] = 'application/json';

const request = (method, url, data) => {
    try {
        return axios({
            method,
            url,
            data
        });
    } catch (error) {
        throw error;
    }
};

export const postRequest = (url, data) => {
    return request("POST", url, data);
}

export const getRequest = (url) => {
    return request("GET", url, {});
}

export const deleteRequest = (url, data) => {
    return request("DELETE", url, data);
}