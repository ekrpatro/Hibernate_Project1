import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class EmpClient {

	public static void main(String[] args) throws Exception {

		Configuration cfg = new Configuration();
		cfg.configure("/hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();

		/* save(), update(), delete(), get(), load(), list(), iterate() */
		Scanner sc = new Scanner(System.in);

		System.out.println(
				"Enter\n 1. Insert\n "
				+ "2. Display Single Record\n "
				+ "3. Delete\n "
				+ "4. Update\n "
				+ "5. Display All"
				+ "\n Enter your choice");
		int option = sc.nextInt();
		sc.nextLine();
		while (option <= 5) {
			switch (option) {
			case 1:
				System.out.println("Enter Ename");
				String ename = sc.nextLine();
				System.out.println("Enter Sal");
				double sal = sc.nextDouble();
				sc.nextLine();
				System.out.println("Enter Desig");
				String desig = sc.nextLine();

				Emp e1 = new Emp(ename, sal, desig);

				Transaction t = s.beginTransaction();
				try {
					s.save(e1);
					s.flush();
					t.commit();
					System.out.println("TX Success");
				} catch (Exception e) {
					t.rollback();
					e.printStackTrace();
					System.out.println("TX Failed");
				}
				break;
			case 2:
				System.out.println("Enter Eid");
				int eid = sc.nextInt();
				Emp e = (Emp) s.get(Emp.class, eid);
				System.out.println(e);
				break;
			case 3:
				System.out.println("Enter Eid");
				eid = sc.nextInt();
				e = (Emp) s.load(Emp.class, eid);
				t = s.beginTransaction();
				try {
					s.delete(e);
					s.flush();
					t.commit();
					System.out.println("Record deleted");
				} catch (Exception ex2) {
					t.rollback();
					ex2.printStackTrace();
					System.out.println("Delete failed");
				}
				break;
			case 4:
				System.out.println("Enter Eid");
				eid = sc.nextInt();
				sc.nextLine();
				System.out.println("Enter Ename");
				ename = sc.nextLine();
				System.out.println("Enter Sal");
				sal = sc.nextDouble();
				sc.nextLine();
				System.out.println("Enter Desig");
				desig = sc.nextLine();
				e = (Emp) s.load(Emp.class, eid);
				e.setEname(ename);
				e.setSal(sal);
				e.setDesig(desig);
				t = s.beginTransaction();
				try {
					s.update(e);
					s.flush();
					t.commit();
					System.out.println("Emp record updated");
				} catch (Exception ex1) {
					t.rollback();
					ex1.printStackTrace();
					System.out.println("TX Failed");
				}
				break;
			case 5:
				Query q = s.createQuery("FROM Emp");
				List<Emp> empList = q.list();
				System.out.println("************************");
				for (Emp e2 : empList) {
					System.out.println(e2);
				}
				System.out.println("************************");
				break;
			}// switch()
			System.out.println(
					"Enter 1. insert\n 2. display record\n 3. delete\n 4. update\n 5. Display All\n Enter your choice");
			option = sc.nextInt();
			sc.nextLine();
		} // while()

		s.close();
	}
}

/*
 * create table emp (eid integer primary key, ename varchar(50), sal double,
 * desig varchar(50) );
 */
