import {Route, Routes} from "react-router-dom";
import NotFoundPage from "../pages/NotFoundPage.tsx";
import RegisterPage from "../pages/RegisterPage";
import {HomePage} from "../pages/HomePage.tsx";
import LoginPage from "../pages/LoginPage.tsx";
import ProfilePage from "../pages/ProfilePage.tsx";
import ForgotPasswordPage from "../pages/ForgotPasswordPage.tsx";
import ResetPasswordPage from "../pages/ResetPasswordPage.tsx";
import PrivateRoute from "./ProtectedRoute.tsx";
import AdminRoute from "./AdminRoute.tsx";
import OrderManagementPage from "../pages/OrderManagementPage.tsx";
import ProductCategoryManagerPage from "../pages/ProductCategoryManagerPage.tsx";
import ProductManagerPage from "../pages/ProductManagerPage.tsx";
import ProductDetailPage from "../pages/ProductDetailPage.tsx";
import CartPage from "../pages/CartPage.tsx";
import CheckoutPage from "../pages/CheckoutPage.tsx";
import AssignCategoryPage from "../pages/AssignCategoryPage.tsx";
import AboutPage from "../pages/AboutPage.tsx";
import ContactPage from "../pages/ContactPage.tsx";
import OrderDetailPage from "../pages/OrderDetailPage.tsx";
import {RecommendationPage} from "../pages/RecommendationPage.tsx";

export const ROUTES = {
    INDEX: "/",
    HOME: "/home",
    LOGIN: "/login",
    REGISTER: "/register",
    FORGOT_PASSWORD: "/forgot-password",
    RESET_PASSWORD: "/reset-password",
    PROFILE: "/profile/:username",
    PRODUCTS: "/products",
    PRODUCT_CATEGORIES: "/product-categories",
    ASSIGN_PRODUCT_CATEGORIES: "/assign-product-categories",
    PRODUCT_DETAILS: "/products/:id",
    ORDER_MANAGEMENT: "/order-management",
    ORDER_DETAILS: "/orders/:orderId",
    CART: "/cart",
    CHECKOUT: "/checkout",
    RECOMMENDATIONS: "/recommendations",
    ABOUT: "/about",
    CONTACT: "/contact",
    ADMIN_ROUTE: "/admin",
    NOT_FOUND: "*"
} as const;

export default function AppRouter() {
    return (
        <Routes>
            <Route path={ROUTES.REGISTER} element={<RegisterPage/>}/>
            <Route path={ROUTES.LOGIN} element={<LoginPage/>}/>
            <Route path={ROUTES.FORGOT_PASSWORD} element={<ForgotPasswordPage/>}/>
            <Route path={ROUTES.RESET_PASSWORD} element={<ResetPasswordPage/>}/>

            <Route path={ROUTES.HOME} element={<PrivateRoute><HomePage/></PrivateRoute>}/>
            <Route path={ROUTES.INDEX} element={<PrivateRoute><HomePage/></PrivateRoute>}/>
            <Route path={ROUTES.PRODUCTS} element={<PrivateRoute><ProductManagerPage/></PrivateRoute>}/>
            <Route path={ROUTES.PRODUCT_DETAILS} element={<PrivateRoute><ProductDetailPage/></PrivateRoute>}/>
            <Route path={ROUTES.CART} element={<PrivateRoute><CartPage/></PrivateRoute>}/>
            <Route path={ROUTES.CHECKOUT} element={<PrivateRoute><CheckoutPage/></PrivateRoute>}/>
            <Route path={ROUTES.RECOMMENDATIONS} element={<PrivateRoute><RecommendationPage/></PrivateRoute>}/>
            <Route path={ROUTES.PROFILE} element={<PrivateRoute><ProfilePage/></PrivateRoute>}/>
            <Route path={ROUTES.ORDER_DETAILS} element={<PrivateRoute><OrderDetailPage/></PrivateRoute>}/>
            <Route path={ROUTES.ABOUT} element={<PrivateRoute><AboutPage/></PrivateRoute>}/>
            <Route path={ROUTES.CONTACT} element={<PrivateRoute><ContactPage/></PrivateRoute>}/>
            <Route path={ROUTES.ORDER_MANAGEMENT} element={<OrderManagementPage/>}/>

            <Route path={ROUTES.PRODUCT_CATEGORIES} element={<AdminRoute><ProductCategoryManagerPage/></AdminRoute>}/>
            <Route path={ROUTES.ASSIGN_PRODUCT_CATEGORIES} element={<AdminRoute><AssignCategoryPage/></AdminRoute>}/>

            <Route path={ROUTES.NOT_FOUND} element={<NotFoundPage/>}/>
        </Routes>
    );
}