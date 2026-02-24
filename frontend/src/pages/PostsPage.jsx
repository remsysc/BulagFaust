import React, { useState, useEffect, useCallback } from 'react'
import { getPosts, getCategories, getTags } from '../lib/api.js'

function PostCard({ post }) {
  const date = post.createdAt
    ? new Date(post.createdAt).toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' })
    : '—'

  return (
    <article className="post-card">
      <div className="post-meta">
        <span className={post.status === 'PUBLISHED' ? 'status-published' : 'status-draft'}>
          {post.status === 'PUBLISHED' ? '● PUBLISHED' : '○ DRAFT'}
        </span>
        <span>by {post.author?.username ?? '—'}</span>
        <span>{date}</span>
        {post.readingTime && <span>{post.readingTime} min read</span>}
      </div>
      <div className="post-title">{post.title}</div>
      <div className="post-excerpt">{post.content}</div>
      <div className="chips">
        {post.categories?.map(c => (
          <span key={c.id} className="badge">{c.name}</span>
        ))}
        {post.tags?.map(t => (
          <span key={t.id} className="badge accent">#{t.name}</span>
        ))}
      </div>
    </article>
  )
}

function SkeletonCard() {
  return (
    <div className="card" style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-3)' }}>
      <div className="skeleton" style={{ height: '12px', width: '40%' }} />
      <div className="skeleton" style={{ height: '22px', width: '75%' }} />
      <div className="skeleton" style={{ height: '14px', width: '100%' }} />
      <div className="skeleton" style={{ height: '14px', width: '85%' }} />
      <div style={{ display: 'flex', gap: 'var(--space-2)', marginTop: 'var(--space-2)' }}>
        <div className="skeleton" style={{ height: '22px', width: '60px' }} />
        <div className="skeleton" style={{ height: '22px', width: '60px' }} />
      </div>
    </div>
  )
}

export default function PostsPage() {
  const [posts, setPosts] = useState([])
  const [categories, setCategories] = useState([])
  const [tags, setTags] = useState([])
  const [categoryId, setCategoryId] = useState(null)
  const [tagId, setTagId] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  // Load sidebar data once
  useEffect(() => {
    Promise.all([getCategories(), getTags()])
      .then(([cats, ts]) => { setCategories(cats ?? []); setTags(ts ?? []) })
      .catch(console.error)
  }, [])

  // Reload posts when filters change
  const loadPosts = useCallback(() => {
    setLoading(true)
    setError(null)
    getPosts(categoryId, tagId)
      .then(data => setPosts(data ?? []))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false))
  }, [categoryId, tagId])

  useEffect(() => { loadPosts() }, [loadPosts])

  function toggleCategory(id) {
    setCategoryId(prev => prev === id ? null : id)
    setTagId(null)
  }
  function toggleTag(id) {
    setTagId(prev => prev === id ? null : id)
    setCategoryId(null)
  }

  return (
    <div className="page-enter" style={{ display: 'flex', gap: 'var(--space-8)', alignItems: 'flex-start' }}>
      {/* ── Sidebar ───────────────────────────────────────────────────── */}
      <aside className="sidebar">
        <div className="sidebar-section">
          <div className="sidebar-label">Categories</div>
          <div
            className={`filter-item${!categoryId && !tagId ? ' active' : ''}`}
            onClick={() => { setCategoryId(null); setTagId(null) }}
          >
            <span>All posts</span>
          </div>
          {categories.map(c => (
            <div
              key={c.id}
              className={`filter-item${categoryId === c.id ? ' active' : ''}`}
              onClick={() => toggleCategory(c.id)}
            >
              <span>{c.name}</span>
              <span className="filter-count">{c.postCount}</span>
            </div>
          ))}
        </div>

        <div className="sidebar-section">
          <div className="sidebar-label">Tags</div>
          {tags.length === 0 && <p style={{ fontSize: '0.8rem' }}>No tags yet</p>}
          <div style={{ display: 'flex', flexWrap: 'wrap', gap: 'var(--space-2)' }}>
            {tags.map(t => (
              <span
                key={t.id}
                className={`badge${tagId === t.id ? ' accent' : ''}`}
                onClick={() => toggleTag(t.id)}
                style={{ cursor: 'pointer' }}
              >
                #{t.name}
                {t.postCount > 0 && <span style={{ marginLeft: '4px', opacity: 0.6 }}>{t.postCount}</span>}
              </span>
            ))}
          </div>
        </div>
      </aside>

      {/* ── Feed ──────────────────────────────────────────────────────── */}
      <div style={{ flex: 1, minWidth: 0 }}>
        {/* Header row */}
        <div className="flex justify-between items-center" style={{ marginBottom: 'var(--space-6)' }}>
          <div>
            <h2 style={{ fontFamily: 'var(--font-display)', marginBottom: 'var(--space-1)' }}>Posts</h2>
            {(categoryId || tagId) && (
              <p style={{ fontSize: '0.8rem' }}>
                Filtered by{' '}
                {categoryId
                  ? categories.find(c => c.id === categoryId)?.name
                  : `#${tags.find(t => t.id === tagId)?.name}`}
                {' — '}
                <span
                  style={{ color: 'var(--accent-2)', cursor: 'pointer' }}
                  onClick={() => { setCategoryId(null); setTagId(null) }}
                >
                  clear
                </span>
              </p>
            )}
          </div>
          <div style={{ fontFamily: 'var(--font-mono)', fontSize: '0.75rem', color: 'var(--muted)' }}>
            {!loading && `${posts.length} post${posts.length !== 1 ? 's' : ''}`}
          </div>
        </div>

        {error && <div className="banner error" style={{ marginBottom: 'var(--space-4)' }}>{error}</div>}

        {loading ? (
          <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-4)' }}>
            {[1, 2, 3].map(i => <SkeletonCard key={i} />)}
          </div>
        ) : posts.length === 0 ? (
          <div style={{
            textAlign: 'center',
            padding: 'var(--space-10) 0',
            border: '1px dashed var(--rule)',
            borderRadius: 'var(--radius-lg)',
          }}>
            <div style={{ fontSize: '2rem', marginBottom: 'var(--space-4)' }}>📭</div>
            <h3 style={{ color: 'var(--ghost)', fontFamily: 'var(--font-display)' }}>No posts found</h3>
            <p style={{ marginTop: 'var(--space-2)', fontSize: '0.875rem' }}>
              {categoryId || tagId ? 'Try clearing the filter.' : 'No posts have been created yet.'}
            </p>
          </div>
        ) : (
          <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-4)' }}>
            {posts.map((post, i) => (
              <div key={post.id} style={{ animationDelay: `${i * 60}ms` }}>
                <PostCard post={post} />
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  )
}
