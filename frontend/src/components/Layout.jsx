import React from 'react'
import { Outlet, NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '../App.jsx'

const NAV = [
  { to: '/posts',      label: 'Posts',      icon: '📰' },
  { to: '/categories', label: 'Categories', icon: '📂' },
  { to: '/tags',       label: 'Tags',       icon: '🏷' },
]

export default function Layout() {
  const { signOut } = useAuth()
  const navigate = useNavigate()

  function handleSignOut() {
    signOut()
    navigate('/login')
  }

  return (
    <div style={{ display: 'flex', minHeight: '100vh' }}>
      {/* ── Sidebar nav ─────────────────────────────────────────────────── */}
      <nav style={{
        width: '240px',
        background: 'var(--ink-2)',
        borderRight: '1px solid var(--rule)',
        display: 'flex',
        flexDirection: 'column',
        padding: 'var(--space-6) 0',
        position: 'sticky',
        top: 0,
        height: '100vh',
        flexShrink: 0,
      }}>
        {/* Masthead */}
        <div style={{ padding: '0 var(--space-5)', marginBottom: 'var(--space-8)' }}>
          <div style={{
            fontFamily: 'var(--font-display)',
            fontSize: '1.4rem',
            fontWeight: 900,
            letterSpacing: '-0.02em',
            lineHeight: 1,
            color: 'var(--paper)',
          }}>
            Bulag
            <span style={{ color: 'var(--accent)' }}>Faust</span>
          </div>
          <div style={{
            fontFamily: 'var(--font-mono)',
            fontSize: '0.65rem',
            color: 'var(--muted)',
            marginTop: 'var(--space-1)',
            letterSpacing: '0.08em',
            textTransform: 'uppercase',
          }}>
            CMS Dashboard
          </div>
        </div>

        {/* Nav links */}
        <div style={{ flex: 1, padding: '0 var(--space-3)' }}>
          <div style={{
            fontFamily: 'var(--font-mono)',
            fontSize: '0.65rem',
            color: 'var(--muted)',
            padding: '0 var(--space-3)',
            marginBottom: 'var(--space-3)',
            textTransform: 'uppercase',
            letterSpacing: '0.1em',
          }}>
            Content
          </div>
          {NAV.map(({ to, label, icon }) => (
            <NavLink
              key={to}
              to={to}
              style={({ isActive }) => ({
                display: 'flex',
                alignItems: 'center',
                gap: 'var(--space-3)',
                padding: 'var(--space-3) var(--space-4)',
                borderRadius: 'var(--radius)',
                marginBottom: 'var(--space-1)',
                fontSize: '0.9rem',
                fontWeight: 500,
                color: isActive ? 'var(--paper)' : 'var(--ghost)',
                background: isActive ? 'var(--ink-3)' : 'transparent',
                borderLeft: isActive ? '2px solid var(--accent)' : '2px solid transparent',
                transition: 'all 0.15s',
                textDecoration: 'none',
              })}
            >
              <span style={{ fontSize: '1rem' }}>{icon}</span>
              {label}
            </NavLink>
          ))}
        </div>

        {/* Sign out */}
        <div style={{ padding: '0 var(--space-3)' }}>
          <hr className="rule" style={{ marginBottom: 'var(--space-4)' }} />
          <button
            onClick={handleSignOut}
            className="btn-ghost w-full"
            style={{ width: '100%', textAlign: 'left', display: 'flex', alignItems: 'center', gap: 'var(--space-3)' }}
          >
            <span>↩</span> Sign out
          </button>
        </div>
      </nav>

      {/* ── Main content ────────────────────────────────────────────────── */}
      <main style={{
        flex: 1,
        minWidth: 0,
        padding: 'var(--space-8)',
        overflowY: 'auto',
      }}>
        <Outlet />
      </main>
    </div>
  )
}
