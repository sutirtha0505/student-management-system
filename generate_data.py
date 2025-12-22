import uuid
import random
from datetime import date, timedelta

# Configuration
NUM_STUDENTS = 100
SUBJECTS = ["Mathematics", "Physics", "Chemistry", "Computer Science", "English"]
STATUSES = ["Present", "Absent", "Present", "Present", "Present"] 
START_DATE = date(2024, 1, 1)
END_DATE = date(2024, 12, 31)

FIRST_NAMES = [
    "Aarav", "Vihaan", "Vivaan", "Ananya", "Diya", "Advik", "Kabir", "Rohan", "Siddharth", "Kiara",
    "Ishaan", "Arjun", "Zara", "Alya", "Reyansh", "Aryan", "Aditya", "Dhruv", "Saanvi", "Aadhya",
    "Rahul", "Amit", "Priya", "Sneha", "Vikram", "Neha", "Pooja", "Riya", "Karan", "Simran"
]
LAST_NAMES = [
    "Sharma", "Verma", "Gupta", "Malhotra", "Singh", "Patel", "Kumar", "Das", "Roy", "Chopra",
    "Iyer", "Rao", "Reddy", "Nair", "Mehta", "Jain", "Agarwal", "Joshi", "Mishra", "Dubey"
]
CITIES = ["Mumbai", "Delhi", "Bangalore", "Hyderabad", "Pune", "Chennai", "Kolkata", "Ahmedabad", "Jaipur", "Lucknow"]
AREAS = ["MG Road", "Indiranagar", "Koramangala", "Bandra", "Connaught Place", "Whitefield", "Jubilee Hills", "Salt Lake", "Andheri", "Vasant Vihar"]

def generate_sql():
    with open("mock_data.sql", "w") as f:
        f.write("-- Mock Data for Student Management System (Full Year detailed)\n")
        f.write("USE student_management;\n\n")
        f.write("SET autocommit=0;\n") # Faster inserts
        
        student_ids = []
        
        # 1. Generate Students
        print("Generating Students...")
        f.write("-- Insert Students\n")
        seen_emails = set()
        
        # Buffer for bulk insert
        student_values = []
        
        for i in range(NUM_STUDENTS):
            s_id = str(uuid.uuid4())
            student_ids.append(s_id)
            
            fname = random.choice(FIRST_NAMES)
            lname = random.choice(LAST_NAMES)
            name = f"{fname} {lname}"
            
            email = f"{fname.lower()}.{lname.lower()}{random.randint(1, 999)}@college.com"
            while email in seen_emails:
                 email = f"{fname.lower()}.{lname.lower()}{random.randint(1, 9999)}@college.com"
            seen_emails.add(email)
            
            phone = f"{random.randint(6,9)}{random.randint(100000000, 999999999)}"
            city = random.choice(CITIES)
            area = random.choice(AREAS)
            pincode = random.randint(110000, 800000)
            address = f"Flat {random.randint(1, 500)}, {area}, {city} - {pincode}"
            
            student_values.append(f"('{s_id}', '{name}', '{email}', '{phone}', '{address}')")
            
        f.write("INSERT INTO students (id, name, email, phone, address) VALUES \n")
        f.write(",\n".join(student_values) + ";\n")
        
        
        # 2. Generate Attendance (Every day of the year)
        print("Generating Attendance (this may take a moment)...")
        f.write("\n-- Insert Attendance\n")
        
        delta = END_DATE - START_DATE
        attendance_values = []
        BATCH_SIZE = 5000 # Commit every 5000 records to keep file manageable? No, just bulk insert chunks.
        
        total_records = 0
        
        for i in range(delta.days + 1):
            current_date = START_DATE + timedelta(days=i)
            day_str = current_date.isoformat()
            
            # Skip Sundays? (Optional, but let's keep it 'every day' as requested or maybe skip if weekday=6)
            # if current_date.weekday() == 6: continue 

            for s_id in student_ids:
                status = random.choice(STATUSES)
                attendance_values.append(f"('{s_id}', '{day_str}', '{status}')")
                
                if len(attendance_values) >= 1000:
                    f.write("INSERT INTO attendance (student_id, date, status) VALUES \n")
                    f.write(",\n".join(attendance_values) + ";\n")
                    attendance_values = []
                    total_records += 1000
        
        # Remaining attendance
        if attendance_values:
            f.write("INSERT INTO attendance (student_id, date, status) VALUES \n")
            f.write(",\n".join(attendance_values) + ";\n")

        print(f"  -> Generated approx {total_records} attendance records.")

        f.write("\n-- Insert Marks\n")
        # 3. Generate Marks
        print("Generating Marks...")
        marks_values = []
        for s_id in student_ids:
            for subject in SUBJECTS:
                marks = round(random.uniform(35, 100), 2)
                marks_values.append(f"('{s_id}', '{subject}', {marks})")
        
        f.write("INSERT INTO marks (student_id, subject, marks_obtained) VALUES \n")
        f.write(",\n".join(marks_values) + ";\n")
        
        f.write("COMMIT;\n")
        f.write("SET autocommit=1;\n")

    print(f"Done! Generated mock_data.sql with {NUM_STUDENTS} students and full-year attendance.")

if __name__ == "__main__":
    generate_sql()
