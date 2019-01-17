import {Component} from '@angular/core';
import {NavController, LoadingController, ToastController} from 'ionic-angular';
import {Signup} from "../signup/signup";
import { AuthenticationService } from './../../services/authentication';
import {finalize} from 'rxjs/operators/finalize';

@Component({
  selector: 'page-login',
  templateUrl: 'login.html'
})
export class Login {

  constructor(private navCtrl: NavController,
              private loadingCtrl: LoadingController,
              private authenticationService: AuthenticationService,
              private toastCtrl: ToastController) {
  }

  signup() {
    this.navCtrl.push(Signup);
  }

  login(value: any) {
    let loading = this.loadingCtrl.create({
      spinner: 'bubbles',
      content: 'Authentification en cours ...'
    });

    loading.present();

    this.authenticationService
      .login(value)
      .pipe(finalize(() => loading.dismiss()))
      .subscribe(
        () => {},
        err => this.handleError(err));
  }

  handleError(error: any) {
    let message: string;
    if (error.status && error.status === 401) {
      message = 'Une erreur est survenue';
    }
    else {
      message = `Unexpected error: ${error.statusText}`;
    }

    const toast = this.toastCtrl.create({
      message,
      duration: 5000,
      position: 'bottom'
    });

    toast.present();
  }

}
