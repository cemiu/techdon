import React from 'react';

export default function Basket(props) {
  const { applyItems, onRemove } = props;

  return (
    <aside className="block col-1">
      <h2>Items Applied For</h2>
      <div>
        {applyItems.length === 0 && <div>None </div>}
              {applyItems.map((item) => (
          <div key={item.id} className="row">
            <div className="col-2">{item.name}</div>
            <div className="col-2">
              <button onClick={() => onRemove(item)} className="remove">
                -
              </button>{' '}
           
            </div>

          
          </div>
        ))}

              {applyItems.length !== 0 && (
          <>
            
            
            <div className="row">
              <button onClick={() => alert('Saved Changes!')}>
                              Save Changes
              </button>
            </div>
          </>
        )}
      </div>
    </aside>
  );
}
