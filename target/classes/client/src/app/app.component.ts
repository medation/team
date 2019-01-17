import {Component} from '@angular/core';
import {Platform} from 'ionic-angular';
import {StatusBar} from '@ionic-native/status-bar';
import {SplashScreen} from '@ionic-native/splash-screen';
import {Home} from '../pages/home/home';
import {Login} from "../pages/login/login";
import {AuthenticationService} from "../services/authentication";

@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  
  rootPage: any = null;

  constructor(platform: Platform,
              statusBar: StatusBar,
              splashScreen: SplashScreen,
              authenticationService: AuthenticationService) {

    platform.ready().then(() => {
      statusBar.styleDefault();
      splashScreen.hide();
    });

    authenticationService.authUser.subscribe(jwt => {
      if (jwt) {
        this.rootPage = Home;
      }
      else {
        this.rootPage = Login;
      }
    });

    authenticationService.checkLogin();

  }
}
