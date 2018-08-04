package cn.et.lesson02.service;

import cn.et.lesson02.entity.Emp2;

public interface Emp2Service {
	public void saveEmp(Emp2 s); 
	public void deleteEmp(Integer empno);
	public void updateEmp(Emp2 s);
}