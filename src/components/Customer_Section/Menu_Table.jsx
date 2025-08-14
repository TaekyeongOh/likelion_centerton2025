import React, { useState } from 'react';
import table from '../../assets/img/cus_menu/table.svg'

const numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

function NumberSelector() {
  const [mode, setMode] = useState('circle'); // 'circle' | 'selector' | 'selected'
  const [selected, setSelected] = useState(null);

  return (
    <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
      {mode === 'circle' && (
        <div
          style={{
            width: 57,
            height: 57,
            borderRadius: '50%',
            background: '#58C9F3',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            fontSize: 48,
            cursor: 'pointer',
          }}
          onClick={() => setMode('selector')}
        >
          {/* Circle Icon (원형) */}
          <img src={table} alt="" style={{ width: 23, height: 28 }}/> 
        </div>
      )}

      {mode === 'selector' && (
        <div
          style={{
            width: 57,
            height: 170,
            borderRadius: 28,
            background: '#58C9F3',
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'center',
            overflowY: 'scroll',
          }}
        >
          {numbers.map(n => (
            <div
              key={n}
              style={{ fontSize: 20, margin: 10, cursor: 'pointer', color: 'white' }}
              onClick={() => { setSelected(n); setMode('selected'); }}
            >
              {n}
            </div>
          ))}
        </div>
      )}

      {mode === 'selected' && (
        <div
          style={{
            width: 57,
            height: 57,
            borderRadius: '50%',
            background: '#58C9F3',
            color: '#ffffff',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            fontSize: 20,
            cursor:'pointer',
          }}
        >
          {selected}
        </div>
      )}
    </div>
  );
}

export default NumberSelector;
