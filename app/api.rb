def all_videos_for_semester(level, semester)
  if level == 1 && semester == 1
    Video.where("is_beginner = ? AND semester_number = ?", true, 1).order("week_number")
  elsif level == 2 && semester ==1
    Video.where("is_advanced = ? AND semester_number = ?", true, 1).order("week_number")
  elsif level == 1 && semester == 2
    Video.where("is_beginner = ? AND semester_number = ?", true, 2).order("week_number")
  elsif level == 2 && semester == 2
    Video.where("is_advanced = ? AND semester_number = ?", true, 2).order("week_number")
  elsif level == 2 && semester == 3
    Video.where("is_advanced = ? AND semester_number = ?", true, 3).order("week_number")
  elsif level == 1 && semester == 3
    Video.where("is_beginner = ? AND semester_number = ?", true, 3).order("week_number")
  end
end

# if level == 1
#   Video.where("is_beginner = ? AND semester_number = ?", true, semester).order("week_number")
# elsif level == 2
#   Video.where("is_advanced = ? AND semester_number = ?", true, semester).order("week_number")
