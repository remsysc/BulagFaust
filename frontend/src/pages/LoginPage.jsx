import React, { useState } from 'react'
import { Link, useNavigate, useLocation } from 'react-router-dom'
import { login } from '../lib/api.js'
import { useAuth } from '../App.jsx'

export default function LoginPage() {
  const { signIn } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()
  const from = location.state?.from?.pathname || '/posts'

  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(false)

  async function handleSubmit(e) {
    e.preventDefault()
    setError(null)
    setLoading(true)
    try {
      const auth = await login(email, password)
      signIn(auth)
      navigate(from, { replace: true })
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={{
      minHeight: '100vh',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      padding: 'var(--space-5)',
      background: 'var(--ink)',
    }}>
      {/* Decorative vertical rules */}
      <div style={{ position: 'fixed', inset: 0, pointerEvents: 'none', overflow: 'hidden' }}>
        {[15, 35, 65, 85].map(x => (
          <div key={x} style={{
            position: 'absolute', top: 0, bottom: 0, left: `${x}%`,
            width: '1px', background: 'var(--rule)', opacity: 0.4
          }} />
        ))}
      </div>

      <div className="page-enter" style={{ width: '100%', maxWidth: '400px', position: 'relative' }}>
        {/* Masthead */}
        <div style={{ textAlign: 'center', marginBottom: 'var(--space-8)' }}>
          <div style={{
            fontFamily: 'var(--font-display)',
            fontSize: '2.5rem',
            fontWeight: 900,
            letterSpacing: '-0.02em',
            color: 'var(--paper)',
          }}>
            Bulag<span style={{ color: 'var(--accent)' }}>Faust</span>
          </div>
          <div style={{
            fontFamily: 'var(--font-mono)',
            fontSize: '0.7rem',
            color: 'var(--muted)',
            marginTop: 'var(--space-2)',
            letterSpacing: '0.15em',
            textTransform: 'uppercase',
          }}>
            Content Management System
          </div>
        </div>

        <div className="card" style={{ borderTop: '3px solid var(--accent)' }}>
          <h3 style={{ marginBottom: 'var(--space-5)', fontFamily: 'var(--font-display)' }}>
            Sign in to continue
          </h3>

          {error && <div className="banner error" style={{ marginBottom: 'var(--space-4)' }}>{error}</div>}

          <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-4)' }}>
            <div>
              <label style={{ display: 'block', fontSize: '0.8rem', color: 'var(--muted)', marginBottom: 'var(--space-2)', fontFamily: 'var(--font-mono)', letterSpacing: '0.06em' }}>
                EMAIL
              </label>
              <input
                type="email"
                value={email}
                onChange={e => setEmail(e.target.value)}
                placeholder="you@example.com"
                required
                autoFocus
              />
            </div>
            <div>
              <label style={{ display: 'block', fontSize: '0.8rem', color: 'var(--muted)', marginBottom: 'var(--space-2)', fontFamily: 'var(--font-mono)', letterSpacing: '0.06em' }}>
                PASSWORD
              </label>
              <input
                type="password"
                value={password}
                onChange={e => setPassword(e.target.value)}
                placeholder="••••••••"
                required
              />
            </div>
            <button type="submit" className="btn-primary w-full" disabled={loading} style={{ marginTop: 'var(--space-2)', height: '44px' }}>
              {loading ? <span className="spinner" /> : 'Sign In'}
            </button>
          </form>

          <hr className="rule" />
          <p style={{ textAlign: 'center', fontSize: '0.875rem' }}>
            No account?{' '}
            <Link to="/register" style={{ color: 'var(--accent-2)' }}>Register here</Link>
          </p>
        </div>
      </div>
    </div>
  )
}
