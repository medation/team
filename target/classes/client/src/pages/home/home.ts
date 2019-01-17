import {Utility} from './../../providers/utility';
import { MonumentService } from './../../services/monument.service';
import {Component} from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";
import {AuthenticationService} from "../../services/authentication";
import {HttpClient} from "@angular/common/http";
import {App,NavParams} from 'ionic-angular';
import { Tabs } from '../tabs/tabs';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class Home {

  user: string;
  message: string;
  cities = [];
  regions = [];

  constructor(public app: App,
              private authenticationService: AuthenticationService,
              jwtHelper: JwtHelperService,
              public utility: Utility,
              private httpClient: HttpClient,
              public navParams: NavParams,
              private monumentService : MonumentService) {

    this.authenticationService.authUser.subscribe(jwt => {
      if (jwt) {
        const decoded = jwtHelper.decodeToken(jwt);
        this.user = decoded.sub
      }
      else {
        this.user = null;
      }
    });

  }

  goToApplication() {
     let nav = this.app.getRootNav();
        nav.push(Tabs, {
            "spotType" : "City",
            "user" : this.user
        });
  }

  logout() {
    this.authenticationService.logout();
  }

}
