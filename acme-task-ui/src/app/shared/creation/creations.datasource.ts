import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { Creation, CreationService } from "./creation.service";
import { BehaviorSubject, Observable, of } from "rxjs";
import { catchError, finalize } from "rxjs/operators";

export class CreationsDataSource implements DataSource<Creation> {

  private creationsSubject = new BehaviorSubject<Creation[]>([])
  private loadingSubject = new BehaviorSubject<boolean>(false);

  public loading$ = this.loadingSubject.asObservable();
  public isEmpty$ = true;

  constructor(
    private creationService: CreationService
  ) {
  }

  connect(collectionViewer: CollectionViewer): Observable<Creation[] | ReadonlyArray<Creation>> {
    return this.creationsSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer) {
    this.creationsSubject.complete();
    this.loadingSubject.complete();
  }

  loadCreations(input: string) {
    this.loadingSubject.next(true);

    this.creationService.search(input)
      .pipe(catchError(() => of([])),
        finalize(() => this.loadingSubject.next(false))
      ).subscribe(creations => {
        this.creationsSubject.next(creations);
        this.isEmpty$ = creations.length === 0
    })
  }

}
