import React from 'react'

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
                        <h3>React Multiple Checkbox</h3><br />

                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Hobbies :</label><br />
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" name="Laptop" id="Laptop" value="Laptop" onChange={this.handleInputChange} />
                                    <label class="form-check-label" for="inlineCheckboxh1">Laptop</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" name="USB" id="USB" value="USB" onChange={this.handleInputChange} />
                                    <label class="form-check-label" for="inlineCheckboxh2">USB</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" name="Phone" id="Phone" value="Phone" onChange={this.handleInputChange} />
                                    <label class="form-check-label" for="inlineCheckboxh3">Phone</label>
                                </div>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="col-md-12 text-center">
                                <button type="submit" class="btn btn-primary" onClick={()=>this.submit()}>Submit</button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        )
    }
}

export default MultiCheckBox;