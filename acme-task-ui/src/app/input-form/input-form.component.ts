import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { DataService } from "../shared/data/data.service";

@Component({
  selector: 'app-input',
  templateUrl: './input-form.component.html',
  styleUrls: ['./input-form.component.css']
})
export class InputFormComponent implements OnInit {

  inputForm: FormGroup;
  input: string;

  constructor(
    private formBuilder: FormBuilder,
    private data: DataService
  ) {
  }

  ngOnInit() {
    this.inputForm = this.formBuilder.group({
      input: [null, Validators.required]
    })
    this.data.currentInput.subscribe(input => this.input = input)
  }

  submit() {
    if (!this.inputForm.valid) {
      return
    }
    this.data.changeInput(this.inputForm.controls.input.value)
  }

}
