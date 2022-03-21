import React from 'react'
import './MultiCheckBox.css'
class MultiCheckBox extends React.Component{



    constructor(){
        super();
        this.state = {
            hobbies:[]
        }

        this.handleInputChange = this.handleInputChange.bind(this);
    }





    handleInputChange(event) {
        const target = event.target;
        var value = target.value;

        if(target.checked){
            this.state.hobbies[value] = value;
        }else{
            this.state.hobbies.splice(value, 1);
        }

    }

    submit(){
        console.warn(this.state)
    }



    render(){
        const deviceList = ["laptop", "printer"];

        return(

            <div>
                <div class="row">
                    <div class="col-md-6 offset-md-3">
                        <br /><br />
                        <div class="head">
                        <h3>Choose your technological needs Below!</h3><br />
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                               
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" name="Laptop" id="Laptop" value="Laptop" onChange={this.handleInputChange} />
                                    <label class="form-check-label" for="inlineCheckboxh1"><img className='picture' src="https://static.acer.com/up/Resource/Acer/Laptops/Aspire_Vero/Images/20210927/Aspire-Vero-AV15-51_modelmain.png" />Laptop</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" name="USB" id="USB" value="USB" onChange={this.handleInputChange} />
                                    <label class="form-check-label" for="inlineCheckboxh2"><img className='picture' src="https://pngimg.com/uploads/usb/usb_PNG8834.png" />USB</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" name="Phone" id="Phone" value="Phone" onChange={this.handleInputChange} />
                                    <label class="form-check-label" for="inlineCheckboxh3"><img className='picture' src="https://www.qualcomm.com/etc.clientlibs/qcomm-martech/clientlibs/clientlib-react/resources/static/media/M01-Phone-GlowOn.becbdffc.png" />Phone</label>
                                </div>
                                <div class="form-check1 form-check-inline">
                                      <input class="form-check-input" type="checkbox" name="Hard-Drive" id="Hard-Drive" value="Hard-Drive" onChange={this.handleInputChange} />
                                      <label class="form-check-label" for="inlineCheckboxh3"><img className='picture' src="https://www.solutionsict.co.uk/image/cache/data/Computer-Hard-Drive-Replacement1-700x700.png" />Hard-Drive </label>
                                  </div>
                                  <div class="form-check1 form-check-inline">
                                      <input class="form-check-input" type="checkbox" name="Printer" id="Printer" value="Printer" onChange={this.handleInputChange} />
                                      <label class="form-check-label" for="inlineCheckboxh3"><img className='picture' src="https://i1.adis.ws/i/canon/pixma-ts3350-series-bk-fra_666641ac6f4d46c3996ae9b0cdb63c7d" />Printer</label>
                                  </div>
                                  <div class="form-check1 form-check-inline">
                                      <input class="form-check-input" type="checkbox" name="Mouse" id="Mouse" value="Mouse" onChange={this.handleInputChange} />
                                      <label class="form-check-label" for="inlineCheckboxh3"><img className='picture' src="https://resource.logitechg.com/content/dam/gaming/en/products/refreshed-g203/g203-black-gallery-1.png" />Mouse</label>
                                  </div>
                           
                        </div>
</div>
                        <div class="form-col">
                            
                            <div class="col-md-12 text-center">
                                <button type="submit" class="btn btn-primary" onClick={()=>this.submit()}>Submit</button>
                            </div>
                        
                        </div>
                        
                        
                    </div>
                    <div class="form-row"></div>
                </div>
            </div>
        )
    }
}

export default MultiCheckBox;
