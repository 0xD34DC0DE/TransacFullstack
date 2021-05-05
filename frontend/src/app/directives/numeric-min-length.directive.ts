import {AbstractControl, ValidatorFn} from '@angular/forms';

export function numericMinLength(min: number): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const length = control.value?.toString().length
    return length < min ? {minlength: {value: control.value}} : null;
  };
}