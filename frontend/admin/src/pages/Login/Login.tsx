
import { useEffect, useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { GoogleButton } from "@/components/auth/googleButton.tsx"
import { isAuthenticated, login } from "@/services/authenticationService.ts"
export function Login() {
  const navigate = useNavigate()


  useEffect(() => {
    if(isAuthenticated()) {
      navigate("/")
    }
  },[navigate])

  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState("")
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()

    try {
      const response = await login(username, password)
      console.log("Login successful:", response)
      navigate("/")
    } catch (error) {
      console.error("Login failed:", error)
      setError("Invalid username or password")
    }
  }



  return (
    <div className="min-h-screen w-full flex items-center justify-center bg-gradient-to-br from-background to-card p-4">
      <div className="w-full max-w-md">
        <Card className="w-full border-border/50 shadow-2xl">
        <CardHeader className="space-y-2 text-center">
          <div className="flex justify-center mb-4">
            <div className="w-12 h-12 rounded-lg bg-primary flex items-center justify-center">
              <span className="text-primary-foreground font-bold text-xl">🎬</span>
            </div>
          </div>
          <CardTitle className="text-2xl">Welcome to Cifastar</CardTitle>
          <CardDescription>Manage your cinema easily</CardDescription>
        </CardHeader>
        <CardContent className="space-y-6">
          {error && <div className="p-3 rounded-lg bg-destructive/10 text-destructive text-sm">{error}</div>}

          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="space-y-2">
              <label className="text-sm font-medium">
                Username
              </label>
              <Input
                id="email"
                type="text"
                placeholder="admin@cinema.com"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
                disabled={isLoading}
              />
            </div>

            <div className="space-y-2">
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="text-sm font-medium">
                  Password
                </label>
                <Link to="/auth/forgot-password" className="text-xs text-primary hover:underline">
                  Forget password?
                </Link>
              </div>
              <Input
                id="password"
                type="password"
                placeholder="••••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                disabled={isLoading}
              />
            </div>

            <Button type="submit" className="w-full" disabled={isLoading}>
              {isLoading ? "Logging in..." : "Log In"}
            </Button>
          </form>

          <div className="relative">
            <div className="absolute inset-0 flex items-center">
              <div className="w-full border-t border-border/30"></div>
            </div>
            <div className="relative flex justify-center text-xs uppercase">
              <span className="bg-card px-2 text-muted-foreground">Or</span>
            </div>
          </div>

          <GoogleButton />

          <p className="text-center text-sm text-muted-foreground">
            Don't have an account?{" "}
            <Link to="/auth/signup" className="text-primary hover:underline font-medium">
              Sign up now
            </Link>
          </p>
        </CardContent>
      </Card>
      </div>
    </div>
  )
}

