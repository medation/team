import { Login } from './../login/login';
import {Component, ViewChild} from '@angular/core';
import { LoadingController, ToastController, NavController } from 'ionic-angular';
import {NgModel} from "@angular/forms";
import { AuthenticationService } from './../../services/authentication';
import {finalize} from 'rxjs/operators/finalize';

@Component({
  selector: 'page-signup',
  templateUrl: 'signup.html'
})
export class Signup {

  @ViewChild('username')
  usernameModel: NgModel;

  constructor(private authenticationService: AuthenticationService,
              private navCtrl: NavController,
              private loadingCtrl: LoadingController,
              private toastCtrl: ToastController) {
  }

  signup(value: any) {
    let loading = this.loadingCtrl.create({
      spinner: 'bubbles',
      content: 'Enregistrement en cours ...'
    });

    loading.present();

    this.authenticationService
      .signup(value)
      .pipe(finalize(() => loading.dismiss()))
      .subscribe(
        (jwt) => this.showSuccesToast(jwt),
        err => this.handleError(err));
  }

  login() {
    this.navCtrl.push(Login);
  }

  private showSuccesToast(jwt) {
    if (jwt !== 'EXISTS') {
      const toast = this.toastCtrl.create({
        message: 'Vous avez bien été connécté',
        duration: 3000,
        position: 'bottom'
      });

      toast.present();
    }
    else {
      const toast = this.toastCtrl.create({
        message: 'Ce nom d\'utilisateur existe déja',
        duration: 3000,
        position: 'bottom'
      });

      toast.present();

      this.usernameModel.control.setErrors({'usernameTaken': true});
    }
  }

  handleError(error: any) {
    let message = `Une erreur est survenue`;

    const toast = this.toastCtrl.create({
      message,
      duration: 5000,
      position: 'bottom'
    });

    toast.present();
  }

}
