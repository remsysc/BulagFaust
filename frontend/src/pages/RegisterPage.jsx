import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { register } from '../lib/api.js'
import { useAuth } from '../App.jsx'

export default function RegisterPage() {
  const { signIn } = useAuth()
  const navigate = useNavigate()

  const [form, setForm] = useState({ username: '', email: '', password: '' })
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(false)

  function set(key) {
    return e => setForm(f => ({ ...f, [key]: e.target.value }))
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setError(null)
    setLoading(true)
    try {
      const auth = await register(form.username, form.email, form.password)
      signIn(auth)
      navigate('/posts', { replace: true })
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
      <div style={{ position: 'fixed', inset: 0, pointerEvents: 'none', overflow: 'hidden' }}>
        {[15, 35, 65, 85].map(x => (
          <div key={x} style={{
            position: 'absolute', top: 0, bottom: 0, left: `${x}%`,
            width: '1px', background: 'var(--rule)', opacity: 0.4
          }} />
        ))}
      </div>

      <div className="page-enter" style={{ width: '100%', maxWidth: '400px', position: 'relative' }}>
        <div style={{ textAlign: 'center', marginBottom: 'var(--space-8)' }}>
          <div style={{ fontFamily: 'var(--font-display)', fontSize: '2.5rem', fontWeight: 900, letterSpacing: '-0.02em', color: 'var(--paper)' }}>
            Bulag<span style={{ color: 'var(--accent)' }}>Faust</span>
          </div>
          <div style={{ fontFamily: 'var(--font-mono)', fontSize: '0.7rem', color: 'var(--muted)', marginTop: 'var(--space-2)', letterSpacing: '0.15em', textTransform: 'uppercase' }}>
            Create your account
          </div>
        </div>

        <div className="card" style={{ borderTop: '3px solid var(--accent-2)' }}>
          <h3 style={{ marginBottom: 'var(--space-5)', fontFamily: 'var(--font-display)' }}>Register</h3>

          {error && <div className="banner error" style={{ marginBottom: 'var(--space-4)' }}>{error}</div>}

          <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-4)' }}>
            {[
              { key: 'username', label: 'USERNAME', type: 'text', placeholder: 'johndoe', min: 3 },
              { key: 'email',    label: 'EMAIL',    type: 'email', placeholder: 'you@example.com' },
              { key: 'password', label: 'PASSWORD', type: 'password', placeholder: '••••••••', min: 6 },
            ].map(({ key, label, type, placeholder, min }) => (
              <div key={key}>
                <label style={{ display: 'block', fontSize: '0.8rem', color: 'var(--muted)', marginBottom: 'var(--space-2)', fontFamily: 'var(--font-mono)', letterSpacing: '0.06em' }}>
                  {label}
                </label>
                <input
                  type={type}
                  value={form[key]}
                  onChange={set(key)}
                  placeholder={placeholder}
                  minLength={min}
                  required
                />
              </div>
            ))}
            <button type="submit" className="btn-primary w-full" disabled={loading} style={{ marginTop: 'var(--space-2)', height: '44px' }}>
              {loading ? <span className="spinner" /> : 'Create Account'}
            </button>
          </form>

          <hr className="rule" />
          <p style={{ textAlign: 'center', fontSize: '0.875rem' }}>
            Already have an account?{' '}
            <Link to="/login" style={{ color: 'var(--accent-2)' }}>Sign in</Link>
          </p>
        </div>
      </div>
    </div>
  )
}
