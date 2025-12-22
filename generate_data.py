import uuid
import random

# Configuration
NUM_STUDENTS = 100
SUBJECTS = ["Mathematics", "Physics", "Chemistry", "Computer Science", "English"]
STATUSES = ["Present", "Absent", "Present", "Present", "Present"] 
START_DATE = "2024-01-01"

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
        f.write("-- Mock Data for Student Management System (Indian Context)\n")
        f.write("USE student_management;\n\n")
        
        student_ids = []
        
        # 1. Generate Students
        f.write("-- Insert Students\n")
        seen_emails = set()
        
        for i in range(NUM_STUDENTS):
            s_id = str(uuid.uuid4())
            student_ids.append(s_id)
            
            fname = random.choice(FIRST_NAMES)
            lname = random.choice(LAST_NAMES)
            name = f"{fname} {lname}"
            
            # Ensure unique email
            email = f"{fname.lower()}.{lname.lower()}{random.randint(1, 999)}@college.com"
            while email in seen_emails:
                 email = f"{fname.lower()}.{lname.lower()}{random.randint(1, 9999)}@college.com"
            seen_emails.add(email)
            
            # Indian Phone Number (Start with 6-9, 10 digits)
            phone = f"{random.randint(6,9)}{random.randint(100000000, 999999999)}"
            
            city = random.choice(CITIES)
            area = random.choice(AREAS)
            pincode = random.randint(110000, 800000)
            address = f"Flat {random.randint(1, 500)}, {area}, {city} - {pincode}"
            
            f.write(f"INSERT INTO students (id, name, email, phone, address) VALUES ('{s_id}', '{name}', '{email}', '{phone}', '{address}');\n")
        
        f.write("\n-- Insert Attendance\n")
        # 2. Generate Attendance
        for s_id in student_ids:
            for day in range(1, 6):
                date = f"2024-01-{day:02d}"
                status = random.choice(STATUSES)
                f.write(f"INSERT INTO attendance (student_id, date, status) VALUES ('{s_id}', '{date}', '{status}');\n")

        f.write("\n-- Insert Marks\n")
        # 3. Generate Marks
        for s_id in student_ids:
            for subject in SUBJECTS:
                marks = round(random.uniform(35, 100), 2)
                f.write(f"INSERT INTO marks (student_id, subject, marks_obtained) VALUES ('{s_id}', '{subject}', {marks});\n")

    print(f"Generated mock_data.sql with {NUM_STUDENTS} students (Indian names).")

if __name__ == "__main__":
    generate_sql()
