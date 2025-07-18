export const DEAL_ENDPOINTS = {
    BASE: "/api",
    IS: "/is",
    PS: "/ps",
    USERS: "/is/users",
    PRODUCT_CATEGORIES: "/ps/product-categories",
    PRODUCTS: "/ps/products",
    ORDERS: "/ps/orders",
    RECOMMENDATIONS: "/ps/recommendations",
    AUTH: "/is/auth"
}

export const HTTP_METHOD = {
    GET: "GET",
    POST: "POST",
    PATCH: "PATCH",
    DELETE: "DELETE"
}
export const TOKEN_KEY = 'token';
export const USER_KEY = 'user';
export const AUTH_HEADER = 'Authorization';

export const buildAuthHeader = (token: string): string => `Bearer ${token}`;

export enum OrderStatus {
    PENDING = "PENDING",
    PROCESSING = "PROCESSING",
    SHIPPING = "SHIPPING",
    DONE = "DONE",
    CANCELLED = "CANCELLED"
}