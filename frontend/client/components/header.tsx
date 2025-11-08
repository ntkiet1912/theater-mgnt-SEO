"use client"

import { useState, useEffect } from "react"
import Link from "next/link"
import { Menu, X, Moon, Sun } from "lucide-react"
import { useTheme } from "next-themes"

export function Header() {
  const [isOpen, setIsOpen] = useState(false)
  const [showAuthModal, setShowAuthModal] = useState<"login" | "register" | null>(null)
  const { theme, setTheme, resolvedTheme } = useTheme()
  const [mounted, setMounted] = useState(false)

  useEffect(() => {
    setMounted(true)
  }, [])

  useEffect(() => {
    if (mounted) {
      console.log("[v0] Theme changed to:", resolvedTheme)
    }
  }, [resolvedTheme, mounted])

  const navItems = [
    { label: "Now Showing", href: "#now-showing" },
    { label: "Coming Soon", href: "#coming-soon" },
    { label: "Membership", href: "#membership" },
    { label: "Facilities", href: "#facilities" },
    { label: "Contact", href: "#contact" },
  ]

  const handleThemeToggle = () => {
    const newTheme = resolvedTheme === "dark" ? "light" : "dark"
    console.log("[v0] Toggling theme to:", newTheme)
    setTheme(newTheme)
  }

  return (
    <>
      <header className="fixed top-0 w-full z-50 bg-background/80 backdrop-blur-md border-b border-border">
        <div className="container-max flex items-center justify-between h-16 px-4 md:px-8">
          {/* Logo */}
          <Link href="/" className="flex items-center gap-2">
            <div className="w-8 h-8 gradient-primary rounded-lg flex items-center justify-center">
              <span className="text-white font-bold text-lg">C</span>
            </div>
            <span className="gradient-text font-bold text-xl hidden sm:inline">CINEPLEX</span>
          </Link>

          {/* Desktop Navigation */}
          <nav className="hidden md:flex items-center gap-8">
            {navItems.map((item) => (
              <a
                key={item.href}
                href={item.href}
                className="text-muted-foreground hover:text-foreground transition-colors text-sm font-medium"
              >
                {item.label}
              </a>
            ))}
          </nav>

          {/* Right Actions */}
          <div className="hidden md:flex items-center gap-4">
            {mounted && (
              <button
                onClick={handleThemeToggle}
                className="p-2 rounded-lg hover:bg-muted transition-colors"
                aria-label="Toggle theme"
                title={`Switch to ${resolvedTheme === "dark" ? "light" : "dark"} mode`}
              >
                {resolvedTheme === "dark" ? <Sun size={20} /> : <Moon size={20} />}
              </button>
            )}

            <button
              onClick={() => setShowAuthModal("login")}
              className="px-4 py-2 rounded-lg border border-border hover:bg-muted transition-colors text-sm font-medium"
            >
              Login
            </button>
            <button
              onClick={() => setShowAuthModal("register")}
              className="px-6 py-2 rounded-lg gradient-primary text-white font-semibold hover:shadow-lg hover:shadow-purple-500/50 transition-all text-sm"
            >
              Sign Up
            </button>
          </div>

          {/* Mobile Menu Button */}
          <button
            className="md:hidden text-foreground hover:text-muted-foreground"
            onClick={() => setIsOpen(!isOpen)}
            aria-label="Toggle menu"
          >
            {isOpen ? <X size={24} /> : <Menu size={24} />}
          </button>
        </div>

        {/* Mobile Navigation */}
        {isOpen && (
          <div className="md:hidden bg-card border-b border-border">
            <nav className="flex flex-col p-4 gap-4">
              {navItems.map((item) => (
                <a
                  key={item.href}
                  href={item.href}
                  className="text-muted-foreground hover:text-foreground transition-colors font-medium"
                  onClick={() => setIsOpen(false)}
                >
                  {item.label}
                </a>
              ))}
              <div className="flex gap-2 pt-4 border-t border-border">
                {mounted && (
                  <button
                    onClick={handleThemeToggle}
                    className="flex-1 px-4 py-2 rounded-lg border border-border hover:bg-muted transition-colors text-sm font-medium flex items-center justify-center gap-2"
                    aria-label="Toggle theme"
                  >
                    {resolvedTheme === "dark" ? <Sun size={18} /> : <Moon size={18} />}
                    {resolvedTheme === "dark" ? "Light" : "Dark"}
                  </button>
                )}
                <button
                  onClick={() => setShowAuthModal("login")}
                  className="flex-1 px-4 py-2 rounded-lg border border-border hover:bg-muted transition-colors text-sm font-medium"
                >
                  Login
                </button>
                <button
                  onClick={() => setShowAuthModal("register")}
                  className="flex-1 px-4 py-2 rounded-lg gradient-primary text-white font-semibold text-sm"
                >
                  Sign Up
                </button>
              </div>
            </nav>
          </div>
        )}
      </header>

      {showAuthModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 backdrop-blur-sm p-4">
          <div className="bg-card border border-border rounded-2xl p-8 max-w-md w-full">
            <button
              onClick={() => setShowAuthModal(null)}
              className="absolute top-4 right-4 text-muted-foreground hover:text-foreground"
            >
              <X size={24} />
            </button>

            <h2 className="text-2xl font-bold mb-6">{showAuthModal === "login" ? "Login" : "Create Account"}</h2>

            <form className="space-y-4">
              {showAuthModal === "register" && (
                <div>
                  <label className="block text-sm font-medium mb-2">Full Name</label>
                  <input
                    type="text"
                    placeholder="Your name"
                    className="w-full px-4 py-2 rounded-lg border border-border bg-background focus:outline-none focus:border-purple-500 transition-colors"
                  />
                </div>
              )}

              <div>
                <label className="block text-sm font-medium mb-2">Email</label>
                <input
                  type="email"
                  placeholder="your@email.com"
                  className="w-full px-4 py-2 rounded-lg border border-border bg-background focus:outline-none focus:border-purple-500 transition-colors"
                />
              </div>

              <div>
                <label className="block text-sm font-medium mb-2">Password</label>
                <input
                  type="password"
                  placeholder="••••••••"
                  className="w-full px-4 py-2 rounded-lg border border-border bg-background focus:outline-none focus:border-purple-500 transition-colors"
                />
              </div>

              <button
                type="submit"
                className="w-full px-6 py-3 rounded-lg gradient-primary text-white font-semibold hover:shadow-lg transition-all"
              >
                {showAuthModal === "login" ? "Login" : "Create Account"}
              </button>
            </form>

            <p className="text-center text-sm text-muted-foreground mt-4">
              {showAuthModal === "login" ? "Don't have an account? " : "Already have an account? "}
              <button
                onClick={() => setShowAuthModal(showAuthModal === "login" ? "register" : "login")}
                className="text-purple-600 hover:text-purple-700 font-semibold"
              >
                {showAuthModal === "login" ? "Sign Up" : "Login"}
              </button>
            </p>
          </div>
        </div>
      )}
    </>
  )
}
