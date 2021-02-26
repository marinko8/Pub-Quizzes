import { RezultatModel } from './../../shared/rezultat-model';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-rezultati-dialog',
  templateUrl: './rezultati-dialog.component.html',
  styleUrls: ['./rezultati-dialog.component.css']
})
export class RezultatiDialogComponent implements OnInit {

  tezina: number=-1;
  ocjena: number=-1;
  constructor(
    private router: Router,
    public dialogRef: MatDialogRef<RezultatiDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any){}

  ngOnInit(): void {
  }

  kraj():void{
    this.dialogRef.close({tezina: this.tezina, ocjena: this.ocjena});
  }

}
