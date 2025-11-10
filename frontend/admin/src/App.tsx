
import { ThemeProvider } from "@/providers/ThemeProvider"
import AllRoute from "./components/AllRoute"

function App() {
  return (
    <ThemeProvider>
      <AllRoute/>
    </ThemeProvider>
  )
}

export default App
