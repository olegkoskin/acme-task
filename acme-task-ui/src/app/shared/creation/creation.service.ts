import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";

export interface Creation {
  title: string;
  creators: string[];
  type: string;
}

@Injectable({
  providedIn: 'root'
})
export class CreationService {

  public CREATION_API = '/v1/creations';

  constructor(private http: HttpClient) {
  }

  search(input: string): Observable<Creation[]> {
    let params = new HttpParams()
    params = params.append('input', input);
    return this.http.get<Creation[]>(this.CREATION_API, { params: params })
  }

}
