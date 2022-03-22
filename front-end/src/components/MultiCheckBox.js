import React, {Component, useEffect, useState} from "react";
import "../App.css"
import StudentService from "../services/student.service";
import "bootstrap/dist/css/bootstrap.min.css";
import {Navigate} from "react-router-dom";
class CheckBox extends Component {


    state = {
        goToHome :false,
        Devices: []
    }

    componentDidMount() {
        if (!StudentService.isStudent()){
            this.state.goToHome = true;
        }
    }

    onChange = (event) =>{
        //console.log(event.target.checked);
        const isChecked = event.target.checked;
        if(isChecked){
            this.setState({ Devices: [...this.state.Devices, event.target.value] });
        }else{
            let index = this.state.Devices.indexOf(event.target.value);
            this.state.Devices.splice(index, 1);
            this.setState({ Devices: this.state.Devices });
        }
    }
    onSubmit = (data) =>{
        data.preventDefault();
        StudentService.setDonationPreferences(this.state.Devices);
    }

    render() {

        return (
            <div>
                {this.state.goToHome && <Navigate to='/'/>}

          <div className="register-form">
              <div className="mb-3">
              <form onSubmit={this.onSubmit}>
                  <h1 className="mt-4 mb-4" > Select Devices.</h1>
                  <input type="checkbox" name="Devices" value="laptop" onChange={this.onChange}/>
                  <label htmlFor="Devices1">laptop</label><br />
                  <input type="checkbox" name="Devices" value="desktopComputer" onChange={this.onChange}/>
                  <label htmlFor="Devices2">Desktop Computer</label><br />
                  <input type="checkbox" name="Devices" value="smartphone" onChange={this.onChange}/>
                  <label htmlFor="Devices3">Smartphone</label><br />
                  <input type="checkbox" name="Devices" value="iPad" onChange={this.onChange}/>
                  <label htmlFor="Devices4">iPad</label><br />
                  <input type="checkbox" name="Devices" value="tablet" onChange={this.onChange}/>
                  <label htmlFor="Devices5">Tablet</label><br />
                  <input type="checkbox" name="Devices" value="printer" onChange={this.onChange}/>
                  <label htmlFor="Devices6">Printer</label>
                  <input type="checkbox" name="Devices" value="monitor" onChange={this.onChange}/>
                  <label htmlFor="Devices5">Monitor</label><br />
                  <input type="checkbox" name="Devices" value="hardDrive" onChange={this.onChange}/>
                  <label htmlFor="Devices6">Hard Drive</label>
                  <input type="checkbox" name="Devices" value="keyboard" onChange={this.onChange}/>
                  <label htmlFor="Devices5">Keyboard</label><br />
                  <input type="checkbox" name="Devices" value="mouse" onChange={this.onChange}/>
                  <label htmlFor="Devices6">Mouse</label>
                  <input type="checkbox" name="Devices" value="usbStick" onChange={this.onChange}/>
                  <label htmlFor="Devices6">USB Stick</label>


                  <br />
                  <input className="btn btn-primary" type='submit' value='Submit'/>
              </form>
          </div>
          </div>
            </div>
        );
    }
}
export default CheckBox;