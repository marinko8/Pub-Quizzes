import { AuthService } from './../auth/shared/auth.service';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @Input() username: string;

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
  }
  login() {
    this.router.navigateByUrl('/login');
  }

  profil(): void {
    this.router.navigateByUrl('/korisnik/' + this.username);
  }

  odjava(): void {
    this.authService.logout();
    this.router.navigateByUrl('');
  }

}
