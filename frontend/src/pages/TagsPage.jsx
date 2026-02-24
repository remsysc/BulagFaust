import React, { useState, useEffect } from 'react'
import { getTags, createTag, deleteTag } from '../lib/api.js'
import Modal from '../components/Modal.jsx'

export default function TagsPage() {
  const [tags, setTags] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  const [showCreate, setShowCreate] = useState(false)
  const [deleteTarget, setDeleteTarget] = useState(null)
  const [newTagName, setNewTagName] = useState('')
  const [submitting, setSubmitting] = useState(false)
  const [formError, setFormError] = useState(null)

  async function load() {
    setLoading(true)
    try {
      const data = await getTags()
      setTags(data ?? [])
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }
  useEffect(() => { load() }, [])

  async function handleCreate(e) {
    e.preventDefault()
    setFormError(null)
    setSubmitting(true)
    try {
      await createTag(newTagName.trim())
      setNewTagName('')
      setShowCreate(false)
      load()
    } catch (err) {
      setFormError(err.message)
    } finally {
      setSubmitting(false)
    }
  }

  async function handleDelete() {
    setFormError(null)
    setSubmitting(true)
    try {
      await deleteTag(deleteTarget.id)
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
      <div className="flex justify-between items-center" style={{ marginBottom: 'var(--space-6)' }}>
        <div>
          <h2 style={{ fontFamily: 'var(--font-display)' }}>Tags</h2>
          <p style={{ marginTop: 'var(--space-1)', fontSize: '0.875rem' }}>
            Fine-grained labels for posts
          </p>
        </div>
        <button
          className="btn-primary"
          onClick={() => { setShowCreate(true); setFormError(null); setNewTagName('') }}
        >
          + New Tag
        </button>
      </div>

      {error && <div className="banner error" style={{ marginBottom: 'var(--space-4)' }}>{error}</div>}

      {loading ? (
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: 'var(--space-3)' }}>
          {[1,2,3,4,5,6].map(i => (
            <div key={i} className="skeleton" style={{ height: '36px', width: `${70 + i * 15}px`, borderRadius: '2px' }} />
          ))}
        </div>
      ) : tags.length === 0 ? (
        <div style={{ textAlign: 'center', padding: 'var(--space-10) 0', border: '1px dashed var(--rule)', borderRadius: 'var(--radius-lg)' }}>
          <div style={{ fontSize: '2rem', marginBottom: 'var(--space-4)' }}>🏷</div>
          <h3 style={{ color: 'var(--ghost)', fontFamily: 'var(--font-display)' }}>No tags yet</h3>
          <p style={{ marginTop: 'var(--space-2)' }}>Create tags to label your posts.</p>
        </div>
      ) : (
        <>
          {/* Summary bar */}
          <div style={{
            fontFamily: 'var(--font-mono)', fontSize: '0.75rem', color: 'var(--muted)',
            marginBottom: 'var(--space-5)',
          }}>
            {tags.length} tag{tags.length !== 1 ? 's' : ''} total
          </div>

          {/* Tag grid */}
          <div style={{
            background: 'var(--ink-2)',
            border: '1px solid var(--rule)',
            borderRadius: 'var(--radius-lg)',
            overflow: 'hidden',
          }}>
            <div style={{
              display: 'grid', gridTemplateColumns: '1fr auto auto',
              padding: 'var(--space-3) var(--space-5)',
              borderBottom: '1px solid var(--rule)',
              fontFamily: 'var(--font-mono)', fontSize: '0.7rem', color: 'var(--muted)',
              textTransform: 'uppercase', letterSpacing: '0.1em',
            }}>
              <span>Tag</span>
              <span style={{ marginRight: 'var(--space-6)' }}>Published Posts</span>
              <span>Action</span>
            </div>

            {tags.map((tag, i) => (
              <div
                key={tag.id}
                style={{
                  display: 'grid', gridTemplateColumns: '1fr auto auto',
                  alignItems: 'center',
                  padding: 'var(--space-4) var(--space-5)',
                  borderBottom: i < tags.length - 1 ? '1px solid var(--rule)' : 'none',
                  transition: 'background 0.1s',
                  animation: `fadeUp 0.3s ease ${i * 40}ms both`,
                }}
                onMouseEnter={e => e.currentTarget.style.background = 'var(--ink-3)'}
                onMouseLeave={e => e.currentTarget.style.background = 'transparent'}
              >
                <div style={{ display: 'flex', alignItems: 'center', gap: 'var(--space-3)' }}>
                  <span style={{ color: 'var(--muted)', fontFamily: 'var(--font-mono)' }}>#</span>
                  <span style={{ fontWeight: 500 }}>{tag.name}</span>
                </div>
                <span style={{
                  fontFamily: 'var(--font-mono)', fontSize: '0.8rem', color: 'var(--muted)',
                  marginRight: 'var(--space-6)',
                }}>
                  {tag.postCount}
                </span>
                <button
                  className="btn-danger btn-sm"
                  onClick={() => { setDeleteTarget(tag); setFormError(null) }}
                >
                  Delete
                </button>
              </div>
            ))}
          </div>
        </>
      )}

      {/* Create modal */}
      {showCreate && (
        <Modal title="New Tag" onClose={() => setShowCreate(false)}>
          {formError && <div className="banner error" style={{ marginBottom: 'var(--space-4)' }}>{formError}</div>}
          <form onSubmit={handleCreate} style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-4)' }}>
            <div>
              <label style={{ display: 'block', fontSize: '0.8rem', color: 'var(--muted)', marginBottom: 'var(--space-2)', fontFamily: 'var(--font-mono)', letterSpacing: '0.06em' }}>
                TAG NAME
              </label>
              <input
                autoFocus
                value={newTagName}
                onChange={e => setNewTagName(e.target.value)}
                placeholder="e.g. javascript"
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

      {/* Delete modal */}
      {deleteTarget && (
        <Modal title="Delete Tag" onClose={() => setDeleteTarget(null)}>
          {formError && <div className="banner error" style={{ marginBottom: 'var(--space-4)' }}>{formError}</div>}
          <p style={{ marginBottom: 'var(--space-5)', color: 'var(--ghost)' }}>
            Delete tag{' '}
            <strong style={{ color: 'var(--accent-2)', fontFamily: 'var(--font-mono)' }}>#{deleteTarget.name}</strong>?
            {deleteTarget.postCount > 0 && (
              <span style={{ color: 'var(--accent)', display: 'block', marginTop: 'var(--space-2)' }}>
                ⚠ This tag has {deleteTarget.postCount} published post{deleteTarget.postCount !== 1 ? 's' : ''} associated.
              </span>
            )}
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
