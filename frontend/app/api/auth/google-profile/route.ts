import { cookies } from 'next/headers'
import { NextResponse } from 'next/server'

export async function GET() {
    const cookieStore = cookies()
    const data = cookieStore.get('accessToken')
    return NextResponse.json({ data })
}

export async function DELETE() {
    const cookieStore = cookies()
    cookieStore.delete('accessToken')
    return NextResponse.json({ message: 'Delete token successed' })
}
