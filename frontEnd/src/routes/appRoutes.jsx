import { createBrowserRouter, Navigate } from "react-router-dom";
import { RootLayout } from "../components/layouts/RootLayout.jsx";
import { AuthLayout } from "../components/layouts/AuthLayout.jsx";
import { DashboardLayout } from "../components/layouts/DashboardLayout.jsx";
import { LandingPage } from "../pages/LandingPage.jsx";
import { Login } from "../pages/auth/Login.jsx";
import { MyOfficers } from "../dashboard/MyOfficers.jsx";
import { CreateOfficer } from "../dashboard/CreateOfficer.jsx";
import { AllVoters } from "../dashboard/AllVoters.jsx";
import { AllBooths } from "../dashboard/AllBooths.jsx";
import { AllPCS } from "../dashboard/AllPCS.jsx";
import { AllACS } from "../dashboard/AllACS.jsx";
import { AllMobilityBooths } from "../dashboard/AllMobilityBooths.jsx";
import { AllStates } from "../dashboard/AllStates.jsx";
import { ECIDashboard } from "../dashboard/ECI_HQ/ECIDashboard.jsx";
import { ProtectedRoutes } from "../components/gaurds/ProtectedRoutes.jsx";
import { CEODashboard } from "../dashboard/CEO/CEODashboard.jsx";
import { DEODashboard } from "../dashboard/DEO/DEODashboard.jsx";
import { ERODashboard } from "../dashboard/ERO/ERODashboard.jsx";
import { BLODashboard } from "../dashboard/BLO/BLODashboard.jsx";
import { ManageVoters } from "../dashboard/BLO/ManageVoters.jsx";
import { VerifyVoters } from "../dashboard/VerifyVoters.jsx";
import { MobilityVerification } from "../dashboard/MobilityVerification.jsx";
import { CreatePollingBoothOfficer } from "../dashboard/CreatePollingBoothOfficer.jsx";

export const appRoutes = createBrowserRouter([
    {
        path: "/",
        element: <RootLayout />,
        children: [
            {
                index: true,
                element: <LandingPage />
            }
        ]
    },
    {
        path: "/login",
        element: <AuthLayout />,
        children: [
            {
                index: true,
                element: <Login />
            }
        ]
    },
    {
        path: "dashboard",
        element: <DashboardLayout />,
        children: [
            {
                index: true,
                element: <Navigate to="/dashboard/eci-hq" replace />
            },
            {
                path: "eci-hq",
                element: <ECIDashboard />
            },
            {
                path: "ceo",
                element: <CEODashboard />
            },
            {
                path: "deo",
                element: <DEODashboard />
            },
            {
                path: "ero",
                element: <ERODashboard />
            },
            {
                path: "blo",
                element: <BLODashboard />
            },
            {
                path: "manage-voters",
                element: <ManageVoters />
            },
            {
                path: "verify-voters",
                element: <VerifyVoters />
            },
            {
                path: "mobility-verification",
                element: <MobilityVerification />
            },
            {
                path: "officers",
                element: <MyOfficers />
            },
            {
                path: "create-officer",
                element: <CreateOfficer />
            },
            {
                path: "create-polling-booth-officer",
                element: <CreatePollingBoothOfficer />
            },
            {
                element: <ProtectedRoutes allowedRoles={["ECI HQ", "CEO"]} />,
                children: [
                    {
                        path: "voters",
                        element: <AllVoters />
                    },
                    {
                        path: "booths",
                        element: <AllBooths />
                    },
                    {
                        path: "mobility-booths",
                        element: <AllMobilityBooths />
                    },
                    {
                        path: "pcs",
                        element: <AllPCS />
                    },
                    {
                        path: "acs",
                        element: <AllACS />
                    },
                    {
                        path: "states",
                        element: <AllStates />
                    }
                ]
            }
        ]
    }
]);
