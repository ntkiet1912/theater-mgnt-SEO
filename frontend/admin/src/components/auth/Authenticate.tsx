import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { setToken } from "@/services/localStorageService.ts";
import axios from "axios";

export default function Authenticate() {
  const navigate = useNavigate();
  const [isLoggedin, setIsLoggedin] = useState(false);

  useEffect(() => {
    const accessTokenRegex = /code=([^&]+)/;
    const isMatch = window.location.href.match(accessTokenRegex);

    if (isMatch) {
      const authCode = isMatch[1];
      console.log("Auth Code:", authCode);

      axios
        .post(
          `http://localhost:8080/api/theater-mgnt/auth/outbound/authenticate/?code=${authCode}`
        )
        .then((response) => {
          console.log("Authentication successful:", response.data);
          setToken(response.data.result?.token);
          setIsLoggedin(true);
        })
        .catch((error) => {
          console.error("Authentication failed:", error);
        });
    }
  }, []);

  useEffect(() => {
    if (isLoggedin) {
      navigate("/");
    }
  }, [isLoggedin, navigate]);

  return (
    <div className="flex flex-col gap-8 justify-center items-center h-screen">
      {/* Loading Spinner */}
      <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>

      {/* Text */}
      <p className="text-lg text-gray-700">Authenticating...</p>
    </div>
  );
}
