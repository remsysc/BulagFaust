import React, { useEffect } from 'react'

export default function Modal({ title, onClose, children }) {
  // Close on Escape key
  useEffect(() => {
    function onKey(e) { if (e.key === 'Escape') onClose() }
    window.addEventListener('keydown', onKey)
    return () => window.removeEventListener('keydown', onKey)
  }, [onClose])

  return (
    <div className="modal-backdrop" onClick={(e) => { if (e.target === e.currentTarget) onClose() }}>
      <div className="modal">
        <div className="flex justify-between items-center" style={{ marginBottom: 'var(--space-4)' }}>
          <h3>{title}</h3>
          <button className="btn-icon" onClick={onClose} style={{ fontSize: '1.1rem' }}>✕</button>
        </div>
        {children}
      </div>
    </div>
  )
}
