import { Outlet, Link } from "react-router-dom"
import { Button } from "../ui/button.jsx"

export const AuthLayout = () => {
    return (
        <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-[#000080] via-[#000080] to-[#FF9933] p-4">
            <div className="w-full max-w-md">
                <div className="text-center mb-8">
                    <Link to="/" className="inline-flex items-center gap-3 mb-6">
                        <div className="w-16 h-16 rounded-full bg-gradient-to-br from-[#FF9933] via-white to-[#138808] p-1 shadow-2xl">
                            <div className="w-full h-full rounded-full bg-white flex items-center justify-center">
                                <span className="text-[#000080] font-bold text-lg">ONOE</span>
                            </div>
                        </div>
                    </Link>
                    <h2 className="text-3xl font-bold text-white drop-shadow-lg">Welcome Back</h2>
                    <p className="text-white/90 mt-2 text-lg">Sign in to your officer account</p>
                </div>
                
                <div className="bg-white/95 backdrop-blur rounded-2xl shadow-2xl p-8 border border-white/20">
                    <Outlet />
                </div>
                
                <div className="flex items-center justify-center gap-4 mt-6">
                    <div className="w-8 h-2 rounded-full bg-[#FF9933]"></div>
                    <div className="w-8 h-2 rounded-full bg-white"></div>
                    <div className="w-8 h-2 rounded-full bg-[#138808]"></div>
                </div>
                
                <p className="text-center text-white/80 mt-4 text-sm font-medium">
                    One Nation, One Election &bull; &copy; 2024
                </p>
            </div>
        </div>
    )
}
