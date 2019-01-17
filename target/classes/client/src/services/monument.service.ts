import { Observable } from 'rxjs/Observable';
import { Injectable }  from '@angular/core';
import { Http, Response } from '@angular/http';
import {HttpClient} from "@angular/common/http";
import {ReplaySubject} from "rxjs";
import {Storage} from "@ionic/storage";
import {JwtHelperService} from "@auth0/angular-jwt";
import 'rxjs/add/operator/map';

const SERVER_URL = "http://localhost:5000/api";

@Injectable()
export class MonumentService {
  
    private jwtTokenName = 'jwt_token';

    authUser = new ReplaySubject<any>(1);

    constructor(private httpClient: HttpClient,
                private http: Http,
                private storage: Storage,
                private jwtHelper: JwtHelperService) {
	}

    public getCities(): Observable<any> {
        const url = `${SERVER_URL}/cities`;
        return this.httpClient.get(url);    
    }

    public getRegions(): Observable<any> {
        const url = `${SERVER_URL}/regions`;
        return this.httpClient.get(url);   
    }

    public getAllMonuments(): Observable<any> {
        const url = `${SERVER_URL}/monuments`;
        return this.httpClient.get(url);  
    }

    public getMonuments(type: string, name : string): Observable<any> {
        const url = `${SERVER_URL}/${type}/${name}/monuments`;
        return this.httpClient.get(url);  
    }

    public addMonumentFavorie(id : string) : Observable<any>{
        const url = `${SERVER_URL}/${id}/addMonument`;
        return this.httpClient.get(url);
    }

    public getFavories() : Observable<any>{
        const url = `${SERVER_URL}/favories`;
        return this.httpClient.get(url);
    }

    public deleteMonumentFavorie(id : string): Observable<any> {
        const url = `${SERVER_URL}/${id}/deleteMonumentFavorie`;
        return this.httpClient.delete(url);    
    }

}