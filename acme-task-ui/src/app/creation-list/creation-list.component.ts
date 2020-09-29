import { Component, OnInit } from '@angular/core';
import { CreationService } from "../shared/creation/creation.service";
import { DataService } from "../shared/data/data.service";
import { CreationsDataSource } from "../shared/creation/creations.datasource";

@Component({
  selector: 'app-creation-list',
  templateUrl: './creation-list.component.html',
  styleUrls: ['./creation-list.component.css']
})
export class CreationListComponent implements OnInit {

  displayedColumns: string[] = ['title', 'creators', 'type'];
  dataSource: CreationsDataSource;
  input: string;

  constructor(
    private creationService: CreationService,
    private data: DataService
  ) { }

  ngOnInit(): void {
    this.dataSource = new CreationsDataSource(this.creationService)
    this.data.currentInput.subscribe(input => {
      this.input = input;
      this.dataSource.loadCreations(input);
    })
  }

}
