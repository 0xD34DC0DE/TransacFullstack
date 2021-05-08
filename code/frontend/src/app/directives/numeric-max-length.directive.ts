import {AbstractControl, ValidatorFn} from '@angular/forms';

export function numericMaxLength(max: number): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const length = control.value?.toString().length
    return length > max ? {maxlength: {value: control.value}} : null;
  };
}