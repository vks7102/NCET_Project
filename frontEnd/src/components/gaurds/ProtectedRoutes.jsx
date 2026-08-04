import { Navigate, Outlet } from "react-router-dom";
import { useEffect, useState } from "react";
import { getCurrentOfficer } from "../../api/officer.api";

export const ProtectedRoutes = ({ children, allowedRoles }) => {
    const [officer, setOfficer] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchOfficer = async () => {
            try {
                const response = await getCurrentOfficer();
                setOfficer(response.data);
            } catch (error) {
                console.error("Failed to fetch officer:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchOfficer();
    }, []);

    if (loading) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <div className="w-8 h-8 border-4 border-[#000080] border-t-transparent rounded-full animate-spin"></div>
            </div>
        );
    }

    if (!officer) {
        return <Navigate to="/login" replace />;
    }

    if (allowedRoles && !allowedRoles.includes(officer.role)) {
        return <Navigate to="/dashboard" replace />;
    }

    return children ? children : <Outlet />;
};
