import { Injectable, Inject } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { DOCUMENT } from '@angular/common';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from '../../Objects/user';


@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  public users: User[];
  private document: any;
  private baseurl: string;
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  

  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, @Inject(DOCUMENT) d,   private myRoute: Router)
  {
    this.document = d;
    this.http = http;
    this.baseurl = baseUrl;
  }

  getAllUsers(): Observable <Object> { 
    return this.http.get(this.baseurl + 'getAllUsers', {headers: this.headers});
  }


  getUserPage(pageNumber:number, itemsPerPage:number): Observable <Object> {
    const params = new HttpParams()
                  .set('pageNumber', pageNumber.toString())
                  .set("itemsPerPage", itemsPerPage.toString());
                  
    return this.http.get(this.baseurl + 'getUserPage', {headers: this.headers, params:params});
  }

  getUser(id: number): Observable<any> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get(this.baseurl + 'getUser', {headers: this.headers, params: params});
  }

  addUser(user: User): void {
    this.http.post(this.baseurl + 'addUser', user)
      .subscribe((res) => {
      console.log(res);
      });
  }

  updateUser(user: User) {
    return this.http.post(this.baseurl + 'updateUser', user);
  }

  updateUserPassword(user: User) {
    return this.http.post(this.baseurl + 'updateUserPassword', user);
  }

  updateUserRole(user: User) {
    return this.http.post(this.baseurl + 'updateUserRole', user);
  }

  deleteArticle(user: User): Observable<any>{
    return this.http.post(this.baseurl + 'deleteUser', user);
  }
}