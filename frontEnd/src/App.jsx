import { appRoutes } from "./routes/appRoutes.jsx"
import { RouterProvider } from "react-router-dom"
import { Toaster } from "react-hot-toast"

function App() {
  return (
    <>
      <RouterProvider router={appRoutes} />
      <Toaster 
        position="top-right"
        toastOptions={{
          style: {
            background: '#000080',
            color: '#fff',
            borderRadius: '12px',
            padding: '16px',
            fontSize: '14px',
            boxShadow: '0 10px 40px rgba(0, 0, 128, 0.3)',
          },
          success: {
            iconTheme: {
              primary: '#138808',
              secondary: '#fff',
            },
            style: {
              background: '#fff',
              color: '#000080',
              border: '2px solid #138808',
            },
          },
          error: {
            iconTheme: {
              primary: '#FF9933',
              secondary: '#fff',
            },
            style: {
              background: '#fff',
              color: '#000080',
              border: '2px solid #FF9933',
            },
          },
        }}
      />
    </>
  )
}

export default App
