import React, { createContext, useContext, useState, useEffect } from 'react'
import { BrowserRouter, Routes, Route, Navigate, useNavigate, useLocation } from 'react-router-dom'
import { getToken, getUser, removeToken, removeUser, setToken, setUser } from './lib/api.js'
import LoginPage from './pages/LoginPage.jsx'
import RegisterPage from './pages/RegisterPage.jsx'
import PostsPage from './pages/PostsPage.jsx'
import CategoriesPage from './pages/CategoriesPage.jsx'
import TagsPage from './pages/TagsPage.jsx'
import Layout from './components/Layout.jsx'

// ─── Auth Context ────────────────────────────────────────────────────────────
export const AuthContext = createContext(null)

export function useAuth() {
  return useContext(AuthContext)
}

function AuthProvider({ children }) {
  const [token, setTokenState] = useState(getToken)
  const [user, setUserState] = useState(getUser)

  function signIn(authResponse) {
    setToken(authResponse.token)
    // Store minimal user info from AuthResponse { id, tokenType, token, expiresIn }
    const u = { id: authResponse.id }
    setUser(u)
    setTokenState(authResponse.token)
    setUserState(u)
  }

  function signOut() {
    removeToken()
    removeUser()
    setTokenState(null)
    setUserState(null)
  }

  return (
    <AuthContext.Provider value={{ token, user, signIn, signOut, isAuthenticated: !!token }}>
      {children}
    </AuthContext.Provider>
  )
}

// ─── Route guards ─────────────────────────────────────────────────────────────
function PrivateRoute({ children }) {
  const { isAuthenticated } = useAuth()
  const location = useLocation()
  if (!isAuthenticated) return <Navigate to="/login" state={{ from: location }} replace />
  return children
}

function PublicOnly({ children }) {
  const { isAuthenticated } = useAuth()
  if (isAuthenticated) return <Navigate to="/posts" replace />
  return children
}

// ─── App ──────────────────────────────────────────────────────────────────────
export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<PublicOnly><LoginPage /></PublicOnly>} />
          <Route path="/register" element={<PublicOnly><RegisterPage /></PublicOnly>} />
          <Route element={<PrivateRoute><Layout /></PrivateRoute>}>
            <Route index element={<Navigate to="/posts" replace />} />
            <Route path="/posts" element={<PostsPage />} />
            <Route path="/categories" element={<CategoriesPage />} />
            <Route path="/tags" element={<TagsPage />} />
          </Route>
          <Route path="*" element={<Navigate to="/posts" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}
