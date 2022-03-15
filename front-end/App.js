import React from 'react';
import './App.css';
class App extends React.Component{

  state = {
    products: [
      {
        "_id": "1",
            "title": "Acer Aspire Laptop",
        "src": [
       "https://m.media-amazon.com/images/I/41SAuOaeYQL._AC_SS450_.jpg"
          ],
            "description": "Congratulations!! TechDon has offered you the following device,",
        "content": "Acer Aspire is a Windows 10 laptop with a 15.60-inch display that has a resolution of 1920x1080 pixels. It is powered by a Core i7 processor and it comes with 8GB of RAM. The Acer Aspire packs 1TB of HDD storage. Graphics are powered by Intel HD Graphics 620.",
        "colors":["red","black","crimson","teal"],
        
      }
    ],
 
  };






  render(){
    const {products} = this.state;
    return(
      <div className="app">
        {
          products.map(item =>(
            <div className="details" key={item._id}>
              <div className="big-img">
                <img src={item.src} alt=""/>
              </div>

              <div className="box">
                <div className="row">
                  <h2>{item.title}</h2>
 
                </div>
                

                <p>{item.description}</p>
                <p>{item.content}</p>

                      <button className="cart" onClick={(disable= true) => { alert("Congratulations!! You have accepted the device. You will recieve a conformation email shortly.") }}S>Accept Device</button>
                  <button className="carts" onClick={(disable= true) => { alert("You have Declined the device, wait for more") }}>Decline Device</button>
              </div>
            </div>
          ))
        }
      </div>
    );
  };
}

export default App;
