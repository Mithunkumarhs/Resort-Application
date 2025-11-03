import React from "react";
import { Outlet } from "react-router-dom";
import { Navigate, useLocation} from "react-router-dom"; // ✅ Add Outlet import
import ApiService from "./ApiService"; // ✅ Ensure correct path

export const ProtectedRoute = ({ element }) => {
    const location = useLocation();
    return ApiService.isAuthenticated() ? (
        element
    ) : (
        <Navigate to="/login" replace state={{ from: location }} />
    );
};

export const AdminRoute = ({ element }) => {
    const location = useLocation();
    return ApiService.isAdmin() ? (
        element
    ) : (
        <Navigate to="/login" replace state={{ from: location }} />
    );
};
