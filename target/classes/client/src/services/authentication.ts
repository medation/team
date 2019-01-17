import {Injectable} from "@angular/core";
import {tap} from 'rxjs/operators/tap';
import {ReplaySubject, Observable} from "rxjs";
import {Storage} from "@ionic/storage";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {JwtHelperService} from "@auth0/angular-jwt";
import { Http,Response } from "@angular/http";
  
const SERVER_URL = "http://localhost:8062/auth";

@Injectable()
export class AuthenticationService {

    private jwtTokenName = 'jwt_token';
    
    authUser = new ReplaySubject<any>(1);

    constructor(private httpClient: HttpClient,
                private http: Http,
                private storage: Storage,
                private jwtHelper: JwtHelperService) {
    }

    public checkLogin() {
        this.storage.get(this.jwtTokenName).then(jwt => {       
            if (jwt && !this.jwtHelper.isTokenExpired(jwt)) {
              this.httpClient.get(`${SERVER_URL}/authenticate`).subscribe(() => this.authUser.next(jwt),
              (err) => this.storage.remove(this.jwtTokenName).then(() => this.authUser.next(null)));
              // OR
              // this.authUser.next(jwt);
            }
            else {
                this.storage.remove(this.jwtTokenName).then(() => this.authUser.next(null));
            }
        });
    }

    public login(values: any): Observable<any> {
        return this.httpClient.post(`${SERVER_URL}/login`, values, {responseType: 'text'})
            .pipe(tap(jwt => this.handleJwtResponse(jwt)));
    }

    public logout() {
        this.storage.remove(this.jwtTokenName).then(() => this.authUser.next(null));
    }

    public signup(values: any): Observable<any> {
        return this.httpClient.post(`${SERVER_URL}/registre`, values, {responseType: 'text'})
            .pipe(tap(jwt => {
                if (jwt !== 'EXISTS') {
                    return this.handleJwtResponse(jwt);
                }
            return jwt;
        }));
    }

    private handleJwtResponse(jwt: string) {
        return this.storage.set(this.jwtTokenName, jwt)
            .then(() => this.authUser.next(jwt))
            .then(() => jwt);
    }

}
