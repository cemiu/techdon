import React, {Component} from "react";
import "../App.css"
import StudentService from "../services/student.service";
import "bootstrap/dist/css/bootstrap.min.css";
import {Navigate} from "react-router-dom";
import DeviceService from "../services/device.service";

class CheckBox extends Component {


  state = {
    goToHome: false,
    updateSuccessShow: false,
    Devices: []
  }

  componentDidMount() {
    if (!StudentService.isStudent()) {
      this.state.goToHome = true;
      this.setState({goToHome: true});
    }
  }

  onChange = (event) => {
    //console.log(event.target.checked);
    const isChecked = event.target.checked;
    if (isChecked) {
      this.setState({Devices: [...this.state.Devices, event.target.value]});
    } else {
      let index = this.state.Devices.indexOf(event.target.value);
      this.state.Devices.splice(index, 1);
      this.setState({Devices: this.state.Devices});
    }
  }
  onSubmit = (data) => {
    data.preventDefault();
    StudentService.setDonationPreferences(this.state.Devices).then(resp => {
      if (resp.status === 200) {
        this.setState({updateSuccessShow: true});
        setTimeout(() => {
          this.setState({updateSuccessShow: false});
        }, 3000);
      }
    });
  }

  render() {

    const deviceMap = DeviceService.getDeviceMap();

    return (
      <div>
        {this.state.goToHome && <Navigate to='/'/>}

        <div className="register-form">
          <div className="mb-3">
            <form onSubmit={this.onSubmit}>
              <h1 className="mt-4 mb-4"> Select Devices.</h1>
              {
                Object.keys(deviceMap).map(key => {
                  return (
                    <div className="form-check" key={key}>
                      <input className="form-check-input" type="checkbox" value={key} onChange={this.onChange}/>
                      <label className="form-check-label">
                        {deviceMap[key]}
                      </label>
                    </div>
                  )
                })
              }
              <br/>
              <input className="btn btn-primary" type='submit' value='Submit'/>
              <br/><br/>
              { this.state.updateSuccessShow && (
                <div className="alert alert-success" role="alert">
                  <strong>Success!</strong> Your profile has been updated.
                </div>
              )}
            </form>
          </div>
        </div>
      </div>
    );
  }
}

export default CheckBox;