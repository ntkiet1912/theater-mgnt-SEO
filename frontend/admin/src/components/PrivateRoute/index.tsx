import { Navigate, Outlet } from "react-router-dom";
import { isAuthenticated } from "@/services/authenticationService";

function PrivateRoute(){
  const isLogin = isAuthenticated();
  return(
    <>
      {isLogin ? (<Outlet />) : (<Navigate to="/login"/> )}
    </>
  )
}
export default PrivateRoute;