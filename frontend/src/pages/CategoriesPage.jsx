import React, { useState, useEffect } from 'react'
import { getCategories, createCategory, updateCategory, deleteCategory } from '../lib/api.js'
import Modal from '../components/Modal.jsx'

export default function CategoriesPage() {
  const [categories, setCategories] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  // Modals
  const [showCreate, setShowCreate] = useState(false)
  const [editTarget, setEditTarget] = useState(null)   // { id, name }
  const [deleteTarget, setDeleteTarget] = useState(null) // { id, name }

  // Form state
  const [createName, setCreateName] = useState('')
  const [editName, setEditName] = useState('')
  const [submitting, setSubmitting] = useState(false)
  const [formError, setFormError] = useState(null)

  async function load() {
    setLoading(true)
    try {
      const data = await getCategories()
      setCategories(data ?? [])
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }
  useEffect(() => { load() }, [])

  // ── Create ─────────────────────────────────────────────────────────────────
  async function handleCreate(e) {
    e.preventDefault()
    setFormError(null)
    setSubmitting(true)
    try {
      await createCategory(createName.trim())
      setCreateName('')
      setShowCreate(false)
      load()
    } catch (err) {
      setFormError(err.message)
    } finally {
      setSubmitting(false)
    }
  }

  // ── Update ─────────────────────────────────────────────────────────────────
  function openEdit(cat) {
    setEditTarget(cat)
    setEditName(cat.name)
    setFormError(null)
  }

  async function handleUpdate(e) {
    e.preventDefault()
    setFormError(null)
    setSubmitting(true)
    try {
      await updateCategory(editTarget.id, editName.trim())
      setEditTarget(null)
      load()
    } catch (err) {
      setFormError(err.message)
    } finally {
      setSubmitting(false)
    }
  }

  // ── Delete ─────────────────────────────────────────────────────────────────
  async function handleDelete() {
    setFormError(null)
    setSubmitting(true)
    try {
      await deleteCategory(deleteTarget.id)
      setDeleteTarget(null)
      load()
    } catch (err) {
      setFormError(err.message)
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <div className="page-enter">
      {/* Header */}
      <div className="flex justify-between items-center" style={{ marginBottom: 'var(--space-6)' }}>
        <div>
          <h2 style={{ fontFamily: 'var(--font-display)' }}>Categories</h2>
          <p style={{ marginTop: 'var(--space-1)', fontSize: '0.875rem' }}>
            Organise posts into broad topics
          </p>
        </div>
        <button
          className="btn-primary"
          onClick={() => { setShowCreate(true); setFormError(null); setCreateName('') }}
        >
          + New Category
        </button>
      </div>

      {error && <div className="banner error" style={{ marginBottom: 'var(--space-4)' }}>{error}</div>}

      {/* Table */}
      {loading ? (
        <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-3)' }}>
          {[1,2,3,4].map(i => <div key={i} className="skeleton" style={{ height: '56px' }} />)}
        </div>
      ) : categories.length === 0 ? (
        <div style={{ textAlign: 'center', padding: 'var(--space-10) 0', border: '1px dashed var(--rule)', borderRadius: 'var(--radius-lg)' }}>
          <div style={{ fontSize: '2rem', marginBottom: 'var(--space-4)' }}>📂</div>
          <h3 style={{ color: 'var(--ghost)', fontFamily: 'var(--font-display)' }}>No categories yet</h3>
          <p style={{ marginTop: 'var(--space-2)' }}>Create one to start organising your posts.</p>
        </div>
      ) : (
        <div style={{
          background: 'var(--ink-2)',
          border: '1px solid var(--rule)',
          borderRadius: 'var(--radius-lg)',
          overflow: 'hidden',
        }}>
          {/* Table header */}
          <div style={{
            display: 'grid', gridTemplateColumns: '1fr auto auto',
            padding: 'var(--space-3) var(--space-5)',
            borderBottom: '1px solid var(--rule)',
            fontFamily: 'var(--font-mono)', fontSize: '0.7rem', color: 'var(--muted)',
            textTransform: 'uppercase', letterSpacing: '0.1em',
          }}>
            <span>Name</span>
            <span style={{ marginRight: 'var(--space-6)' }}>Posts</span>
            <span>Actions</span>
          </div>

          {categories.map((cat, i) => (
            <div
              key={cat.id}
              style={{
                display: 'grid', gridTemplateColumns: '1fr auto auto',
                alignItems: 'center',
                padding: 'var(--space-4) var(--space-5)',
                borderBottom: i < categories.length - 1 ? '1px solid var(--rule)' : 'none',
                transition: 'background 0.1s',
              }}
              onMouseEnter={e => e.currentTarget.style.background = 'var(--ink-3)'}
              onMouseLeave={e => e.currentTarget.style.background = 'transparent'}
            >
              <span style={{ fontWeight: 500 }}>{cat.name}</span>
              <span style={{
                fontFamily: 'var(--font-mono)', fontSize: '0.8rem', color: 'var(--muted)',
                marginRight: 'var(--space-6)',
              }}>
                {cat.postCount}
              </span>
              <div style={{ display: 'flex', gap: 'var(--space-2)' }}>
                <button className="btn-ghost btn-sm" onClick={() => openEdit(cat)}>Edit</button>
                <button className="btn-danger btn-sm" onClick={() => { setDeleteTarget(cat); setFormError(null) }}>Delete</button>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* ── Create modal ─────────────────────────────────────────────────── */}
      {showCreate && (
        <Modal title="New Category" onClose={() => setShowCreate(false)}>
          {formError && <div className="banner error" style={{ marginBottom: 'var(--space-4)' }}>{formError}</div>}
          <form onSubmit={handleCreate} style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-4)' }}>
            <div>
              <label style={{ display: 'block', fontSize: '0.8rem', color: 'var(--muted)', marginBottom: 'var(--space-2)', fontFamily: 'var(--font-mono)', letterSpacing: '0.06em' }}>
                CATEGORY NAME
              </label>
              <input
                autoFocus
                value={createName}
                onChange={e => setCreateName(e.target.value)}
                placeholder="e.g. Technology"
                minLength={3}
                maxLength={50}
                required
              />
            </div>
            <div style={{ display: 'flex', gap: 'var(--space-3)', justifyContent: 'flex-end' }}>
              <button type="button" className="btn-ghost" onClick={() => setShowCreate(false)}>Cancel</button>
              <button type="submit" className="btn-primary" disabled={submitting}>
                {submitting ? <span className="spinner" /> : 'Create'}
              </button>
            </div>
          </form>
        </Modal>
      )}

      {/* ── Edit modal ───────────────────────────────────────────────────── */}
      {editTarget && (
        <Modal title={`Edit "${editTarget.name}"`} onClose={() => setEditTarget(null)}>
          {formError && <div className="banner error" style={{ marginBottom: 'var(--space-4)' }}>{formError}</div>}
          <form onSubmit={handleUpdate} style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-4)' }}>
            <div>
              <label style={{ display: 'block', fontSize: '0.8rem', color: 'var(--muted)', marginBottom: 'var(--space-2)', fontFamily: 'var(--font-mono)', letterSpacing: '0.06em' }}>
                NEW NAME
              </label>
              <input
                autoFocus
                value={editName}
                onChange={e => setEditName(e.target.value)}
                minLength={3}
                maxLength={50}
                required
              />
            </div>
            <div style={{ display: 'flex', gap: 'var(--space-3)', justifyContent: 'flex-end' }}>
              <button type="button" className="btn-ghost" onClick={() => setEditTarget(null)}>Cancel</button>
              <button type="submit" className="btn-primary" disabled={submitting}>
                {submitting ? <span className="spinner" /> : 'Save Changes'}
              </button>
            </div>
          </form>
        </Modal>
      )}

      {/* ── Delete confirmation modal ─────────────────────────────────── */}
      {deleteTarget && (
        <Modal title="Delete Category" onClose={() => setDeleteTarget(null)}>
          {formError && <div className="banner error" style={{ marginBottom: 'var(--space-4)' }}>{formError}</div>}
          <p style={{ marginBottom: 'var(--space-5)', color: 'var(--ghost)' }}>
            Are you sure you want to delete{' '}
            <strong style={{ color: 'var(--paper)' }}>{deleteTarget.name}</strong>?
            This cannot be undone. Categories with posts cannot be deleted.
          </p>
          <div style={{ display: 'flex', gap: 'var(--space-3)', justifyContent: 'flex-end' }}>
            <button className="btn-ghost" onClick={() => setDeleteTarget(null)}>Cancel</button>
            <button className="btn-danger" onClick={handleDelete} disabled={submitting}>
              {submitting ? <span className="spinner" /> : 'Delete'}
            </button>
          </div>
        </Modal>
      )}
    </div>
  )
}
