import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  login() {
    this.router.navigateByUrl('/login');
  }
  signUp() {
    this.router.navigateByUrl('/sign-up')
  }
  bezPrijave() {
    this.router.navigateByUrl('/kvizovi/1/neprijavljen')
  }

}
