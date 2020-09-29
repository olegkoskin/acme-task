import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private inputSource = new BehaviorSubject('');
  currentInput = this.inputSource.asObservable();

  constructor() { }

  changeInput(message: string) {
    this.inputSource.next(message)
  }

}
