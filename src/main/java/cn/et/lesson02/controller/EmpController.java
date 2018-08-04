package cn.et.lesson02.controller;



import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.et.lesson02.entity.Emp;
import cn.et.lesson02.entity.Result;
import cn.et.lesson02.service.EmpService;
import cn.et.lesson02.utils.PageTools;




@RestController
public class EmpController {
	@Autowired
	EmpService emp;
	@ResponseBody
	@RequestMapping("queryEmp")
	//在参数上添加 (@RequestParam(required=false) String ename)可以不传递参�?
	/*
	 * page  当前�?
	 * rows  每页显示的行�?
	 * */
	public PageTools queryEmp(String ename,Integer page,Integer rows) {
		return emp.queryEmp1(ename,page,rows);
	}
	
	
	
	//新增
	@ResponseBody
	@RequestMapping(value="saveEmp",method=RequestMethod.POST)
	public Result saveEmp(Emp e,MultipartFile file) throws IllegalStateException, IOException{
		//获取问件�?
		String fileName=file.getOriginalFilename();
		File destFile=new File("E:\\Myfile\\"+fileName);
		file.transferTo(destFile);
		/*Result r=new Result();
		r.setCode(1);
		try {
			emp.saveEmp(e);
		} catch (Exception e1) {
			r.setCode(0);
			r.setMessage(e1);
		}
		return r;*/
		return null;
	}
	

	
	//删除
	@ResponseBody
	@RequestMapping(value="deleteEmp/{empno}",method=RequestMethod.DELETE)
	public Result deleteEmp(@PathVariable String empno){
		String arr[]=empno.split(",");
		Result r=new Result();
		r.setCode(1);
		try {
			String a=null;
			a.toString();
			for(int i=0;i<arr.length;i++){
				emp.deleteEmp(Integer.parseInt(arr[i]));
			}
		} catch (Exception e) {
			r.setCode(0);
			r.setMessage(e);
		}
		return r;
	}
	
	
	
	//修改
	@ResponseBody
	@RequestMapping(value="updateEmp/{empno}",method=RequestMethod.PUT)
	public Result updateEmp(@PathVariable Integer empno,Emp e) throws ParseException{
		e.setEmpno(empno);
		Result r=new Result();
		r.setCode(1);
		try {
			emp.updateEmp(e);
		} catch (Exception e1) {
			r.setCode(0);
			r.setMessage(e1);
		}
		return r;
	}
}